package com.datajoy.admin_builder.service;

import com.datajoy.admin_builder.function.FunctionExecutor;
import com.datajoy.admin_builder.function.FunctionFactory;
import com.datajoy.admin_builder.function.FunctionResult;
import com.datajoy.admin_builder.function.ServiceFunction;
import com.datajoy.admin_builder.function.code.ResultType;
import com.datajoy.admin_builder.message.RequestMessage;
import com.datajoy.admin_builder.message.ResponseMessage;
import com.datajoy.admin_builder.security.AuthenticatedUser;
import com.datajoy.admin_builder.security.AuthService;
import com.datajoy.admin_builder.security.SecurityBusinessException;
import com.datajoy.admin_builder.security.TokenUtil;
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
                user = authService.validateAuthentication(TokenUtil.resolveAccessToken(request));
            }
            catch (SecurityBusinessException e) {
                return ResponseMessage.createErrorMessage(e.getStatus(), e.getErrorCode(), e.getErrorMsg());
            }
        }

        if(user != null) {
            authService.validateAuthorization(user);
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
