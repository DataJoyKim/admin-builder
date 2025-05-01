package com.datajoy.admin_builder.apibuilder.service;

import com.datajoy.admin_builder.apibuilder.function.FunctionExecutor;
import com.datajoy.admin_builder.apibuilder.function.FunctionFactory;
import com.datajoy.admin_builder.apibuilder.function.FunctionResult;
import com.datajoy.admin_builder.apibuilder.function.ServiceFunction;
import com.datajoy.admin_builder.apibuilder.function.code.ResultType;
import com.datajoy.admin_builder.apibuilder.message.RequestMessage;
import com.datajoy.admin_builder.apibuilder.message.ResponseMessage;
import com.datajoy.admin_builder.apibuilder.security.AuthenticatedUser;
import com.datajoy.admin_builder.apibuilder.security.AuthService;
import com.datajoy.admin_builder.apibuilder.security.SecurityBusinessException;
import com.datajoy.admin_builder.apibuilder.security.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ServiceBuilderService {
    private final ServiceBuilderRepository serviceRepository;
    private final FunctionFactory functionFactory;
    private final AuthService authService;

    public ResponseMessage execute(
            HttpServletRequest request,
            HttpServletResponse response,
            String serviceName,
            RequestMessage requestMessage
    ) {
        ServiceBuilder serviceBuilder = serviceRepository.findByServiceName(serviceName)
                                            .orElseThrow();

        AuthenticatedUser user = null;
        if(serviceBuilder.getUseAuthValidation()) {
            try {
                user = authService.validateAuthentication(TokenUtil.resolveToken(request));
            }
            catch (SecurityBusinessException e) {
                return ResponseMessage.createErrorMessage(e.getStatus(), e.getErrorCode(), e.getErrorMsg());
            }
        }

        Map<String, List<Map<String, Object>>> contents = executeFunction(requestMessage, user, serviceBuilder.getServiceFunctions());

        return ResponseMessage.createSuccessMessage(contents);
    }

    private Map<String, List<Map<String, Object>>> executeFunction(
            RequestMessage requestMessage,
            AuthenticatedUser user,
            List<ServiceFunction> serviceFunctions
    ) {
        Map<String, List<Map<String, Object>>> contents = new HashMap<>();

        for(ServiceFunction func : serviceFunctions) {
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
