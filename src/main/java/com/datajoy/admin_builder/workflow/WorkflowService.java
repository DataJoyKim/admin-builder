package com.datajoy.admin_builder.workflow;

import com.datajoy.admin_builder.dto.RequestMessage;
import com.datajoy.admin_builder.dto.ResponseMessage;
import com.datajoy.admin_builder.executor.script.ScriptEngineExecuteException;
import com.datajoy.admin_builder.function.*;
import com.datajoy.admin_builder.function.code.ResultType;
import com.datajoy.admin_builder.security.domain.AuthenticatedUser;
import com.datajoy.admin_builder.security.domain.GrantedAuthority;
import com.datajoy.admin_builder.security.exception.SecurityBusinessException;
import com.datajoy.admin_builder.security.service.AuthService;
import com.datajoy.admin_builder.security.token.TokenCookie;
import com.datajoy.admin_builder.workflow.code.BranchType;
import com.datajoy.core.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkflowService {
    private final WorkflowRepository workflowRepository;
    private final WorkflowFunctionRepository workflowFunctionRepository;
    private final WorkflowEdgeRepository workflowEdgeRepository;
    private final WorkflowConditionRepository workflowConditionRepository;
    private final WorkflowAuthorityRepository workflowAuthorityRepository;
    private final FunctionFactory functionFactory;
    private final ConditionEvaluator conditionEvaluator;
    private final AuthService authService;

    public ResponseMessage execute(
            HttpServletRequest request,
            HttpServletResponse response,
            RequestMessage requestMessage
    ) {
        try {
            Workflow workflow = findWorkflow(requestMessage);

            AuthenticatedUser user = null;

            if(workflow.getUseAuthValidation()) {
                user = authService.authentication(TokenCookie.resolveAccessToken(request));
            }

            if(user != null) {
                validateAuthorization(user, workflow);
            }

            return executeFunction(requestMessage, user, createGraph(workflow));
        }
        catch (SecurityBusinessException e) {
            return ResponseMessage.createErrorMessage(e.getStatus(), e.getErrorCode(), e.getErrorMsg());
        }
        catch (BusinessException e) {
            return ResponseMessage.createErrorMessage(e.getStatus(), e.getCode(), e.getMsg());
        }
    }

    public void validateAuthorization(AuthenticatedUser user, Workflow workflow) throws BusinessException {
        List<WorkflowAuthority> workflowAuthorities = workflowAuthorityRepository.findByWorkflow(workflow);
        if(workflowAuthorities.isEmpty()) {
            throw new BusinessException(WorkflowErrorMessage.NOT_SETTING_AUTHORITY);
        }

        Map<String, Workflow> authorityMap = new HashMap<>();
        for(WorkflowAuthority workflowAuthority : workflowAuthorities) {
            authorityMap.put(workflowAuthority.getAuthorityCode(), workflowAuthority.getWorkflow());
        }

        if(authorityMap.containsKey(WorkflowAuthority.VALID_PASS)) {
            return;
        }

        List<GrantedAuthority> grantedAuthorities = user.getGrantedAuthorities();
        if(grantedAuthorities.isEmpty()) {
            throw new BusinessException(WorkflowErrorMessage.NOT_HAS_AUTHORITIES);
        }

        boolean hasAuthority = false;
        for(GrantedAuthority authority : grantedAuthorities){
            if(authorityMap.containsKey(authority.getRole())) {
                hasAuthority = true;
                break;
            }
        }

        if(!hasAuthority) {
            throw new BusinessException(WorkflowErrorMessage.PERMISSION_DENIED);
        }
    }

    private Workflow findWorkflow(RequestMessage requestMessage) throws BusinessException {
        Optional<Workflow> opWorkflow = workflowRepository.findByWorkflowCode(requestMessage.getHeader().getWorkflowCode());
        if(opWorkflow.isEmpty()) {
            throw new BusinessException(WorkflowErrorMessage.NOT_FOUND_WORKFLOW);
        }

        return opWorkflow.get();
    }

    private WorkflowGraph createGraph(Workflow workflow) {
        return WorkflowGraph.of(
                workflowFunctionRepository.findByWorkflowId(workflow.getId()),
                workflowEdgeRepository.findByWorkflowId(workflow.getId()),
                workflowConditionRepository.findByWorkflowId(workflow.getId())
        );
    }

    // 시작 노드부터 연결정보(WorkflowEdge)를 따라가며 실행한다.
    // 조건분기 노드는 걸린 조건의 가지 하나로만 이어지므로 나머지 가지의 노드는 실행되지 않는다.
    private ResponseMessage executeFunction(
            RequestMessage requestMessage,
            AuthenticatedUser user,
            WorkflowGraph graph
    ) throws BusinessException {
        Map<String, List<Map<String, Object>>> messageStorage = requestMessage.getBody();

        List<WorkflowFunction> executedFunctions = new ArrayList<>();
        int failureCnt = 0;
        int step = 0;

        WorkflowFunction current = graph.getStartNode();

        while(current != null) {
            if(++step > WorkflowGraph.MAX_EXECUTE_STEP) {
                throw new BusinessException(WorkflowErrorMessage.EXCEED_MAX_EXECUTE_STEP);
            }

            List<Map<String, Object>> params = messageStorage.get(current.getRequestMessageId());
            if(params == null) {
                params = new ArrayList<>();
            }

            if(current.isCondition()) {
                current = nextOfCondition(graph, current, params);
                continue;
            }

            FunctionExecutor executor = functionFactory.instance(current.getFunctionType());

            FunctionResult result = executor.execute(user, current.getFunctionName(), params);

            if(ResultType.FAILURE.equals(result.getResultType())) {
                failureCnt++;
            }

            messageStorage.put(current.getResponseMessageId(), result.getResults());
            executedFunctions.add(current);

            current = graph.next(current, BranchType.DEFAULT);
        }

        if(failureCnt == 0) {
            return ResponseMessage.createSuccessMessage(createResponseData(executedFunctions, messageStorage));
        }
        else if(failureCnt < executedFunctions.size()) {
            return ResponseMessage.createErrorMessage(500, "E-EXE-002", "에러가 발생되었습니다.", messageStorage);
        }
        else {
            return ResponseMessage.createErrorMessage(500, "E-EXE-001", "에러가 발생되었습니다.", messageStorage);
        }
    }

    // 조건은 if, else if ... 순서대로 판정해서 처음 참이 된 가지로 흐른다. 전부 거짓이면 else 가지로 흐른다.
    private WorkflowFunction nextOfCondition(
            WorkflowGraph graph,
            WorkflowFunction condition,
            List<Map<String, Object>> params
    ) throws BusinessException {
        for(WorkflowCondition workflowCondition : graph.conditionsOf(condition)) {
            if(evaluate(workflowCondition.getConditionExpression(), params)) {
                return graph.nextCase(condition, workflowCondition.getBranchId());
            }
        }

        return graph.nextElse(condition);
    }

    private boolean evaluate(String conditionExpression, List<Map<String, Object>> params) throws BusinessException {
        try {
            return conditionEvaluator.evaluate(conditionExpression, params);
        }
        catch (ScriptEngineExecuteException e) {
            throw new BusinessException(WorkflowErrorMessage.FAILURE_CONDITION_EVALUATE);
        }
    }

    private static Map<String, List<Map<String, Object>>> createResponseData(
            List<WorkflowFunction> functions,
            Map<String, List<Map<String, Object>>> messageStorage
    ) {
        Map<String, List<Map<String, Object>>> responseData = new HashMap<>();
        for(WorkflowFunction func : functions) {
            responseData.put(func.getResponseMessageId(), messageStorage.get(func.getResponseMessageId()));
        }

        return responseData;
    }
}
