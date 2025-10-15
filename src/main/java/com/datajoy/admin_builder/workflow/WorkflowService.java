package com.datajoy.admin_builder.workflow;

import com.datajoy.admin_builder.function.FunctionExecutor;
import com.datajoy.admin_builder.function.FunctionFactory;
import com.datajoy.admin_builder.function.FunctionResult;
import com.datajoy.admin_builder.function.WorkflowFunction;
import com.datajoy.admin_builder.function.code.ResultType;
import com.datajoy.admin_builder.message.RequestMessage;
import com.datajoy.admin_builder.message.ResponseMessage;
import com.datajoy.admin_builder.security.*;
import com.datajoy.core.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkflowService {
    private final WorkflowRepository workflowRepository;
    private final WorkflowAuthorityRepository workflowAuthorityRepository;
    private final FunctionFactory functionFactory;
    private final AuthService authService;

    public ResponseMessage execute(
            HttpServletRequest request,
            HttpServletResponse response,
            RequestMessage requestMessage
    ) {
        try {
            Optional<Workflow> opWorkflow = workflowRepository.findByWorkflowName(requestMessage.getHeader().getWorkflowId());
            if(opWorkflow.isEmpty()) {
                throw new BusinessException(WorkflowErrorMessage.NOT_FOUND_WORKFLOW);
            }

            Workflow workflow = opWorkflow.get();

            AuthenticatedUser user = null;

            if(workflow.getUseAuthValidation()) {
                user = authService.validateAuthentication(TokenUtil.resolveAccessToken(request));
            }

            if(user != null) {
                validateAuthorization(user, workflow);
            }

            Map<String, List<Map<String, Object>>> contents = executeFunction(requestMessage, user, workflow.getFunctions());

            return ResponseMessage.createSuccessMessage(contents);
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

    private Map<String, List<Map<String, Object>>> executeFunction(
            RequestMessage requestMessage,
            AuthenticatedUser user,
            List<WorkflowFunction> functions
    ) {
        Map<String, List<Map<String, Object>>> contents = new HashMap<>();

        for(WorkflowFunction func : functions) {
            FunctionExecutor executor = functionFactory.instance(func.getFunctionType());

            List<Map<String, Object>> params = requestMessage.getBody().get(func.getRequestMessageId());

            FunctionResult result = executor.execute(user, func.getFunctionName(), params);

            if(ResultType.FAILURE.equals(result.getResultType())) {
                //TODO resolved
            }

            contents.put(func.getResponseMessageId(), result.getResults());
        }

        return contents;
    }
}
