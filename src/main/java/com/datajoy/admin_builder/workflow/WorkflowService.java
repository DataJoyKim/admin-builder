package com.datajoy.admin_builder.workflow;

import com.datajoy.admin_builder.dto.RequestMessage;
import com.datajoy.admin_builder.dto.ResponseMessage;
import com.datajoy.admin_builder.function.*;
import com.datajoy.admin_builder.function.code.ResultType;
import com.datajoy.admin_builder.security.domain.AuthenticatedUser;
import com.datajoy.admin_builder.security.domain.GrantedAuthority;
import com.datajoy.admin_builder.security.exception.SecurityBusinessException;
import com.datajoy.admin_builder.security.service.AuthService;
import com.datajoy.admin_builder.security.token.TokenCookie;
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
    private final WorkflowAuthorityRepository workflowAuthorityRepository;
    private final FunctionFactory functionFactory;
    private final AuthService authService;

    public ResponseMessage execute(
            HttpServletRequest request,
            HttpServletResponse response,
            RequestMessage requestMessage
    ) {
        try {
            Optional<Workflow> opWorkflow = workflowRepository.findByWorkflowCode(requestMessage.getHeader().getWorkflowCode());
            if(opWorkflow.isEmpty()) {
                throw new BusinessException(WorkflowErrorMessage.NOT_FOUND_WORKFLOW);
            }

            Workflow workflow = opWorkflow.get();

            List<WorkflowFunction> functions = workflowFunctionRepository.findByWorkflowId(workflow.getId());

            AuthenticatedUser user = null;

            if(workflow.getUseAuthValidation()) {
                user = authService.authentication(TokenCookie.resolveAccessToken(request));
            }

            if(user != null) {
                validateAuthorization(user, workflow);
            }

            return executeFunction(requestMessage, user, functions);
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

    private ResponseMessage executeFunction(
            RequestMessage requestMessage,
            AuthenticatedUser user,
            List<WorkflowFunction> functions
    ) {
        Map<String, List<Map<String, Object>>> messageStorage = requestMessage.getBody();

        int failureCnt = 0;
        for(WorkflowFunction func : functions) {
            FunctionExecutor executor = functionFactory.instance(func.getFunctionType());

            List<Map<String, Object>> params = messageStorage.get(func.getRequestMessageId());
            if(params == null) {
                params = new ArrayList<>();
            }

            FunctionResult result = executor.execute(user, func.getFunctionName(), params);

            if(ResultType.FAILURE.equals(result.getResultType())) {
                failureCnt++;
            }

            messageStorage.put(func.getResponseMessageId(), result.getResults());
        }

        if(failureCnt == 0) {
            return ResponseMessage.createSuccessMessage(createResponseData(functions, messageStorage));
        }
        else if(failureCnt < functions.size()) {
            return ResponseMessage.createErrorMessage(500, "E-EXE-002", "에러가 발생되었습니다.", messageStorage);
        }
        else {
            return ResponseMessage.createErrorMessage(500, "E-EXE-001", "에러가 발생되었습니다.", messageStorage);
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
