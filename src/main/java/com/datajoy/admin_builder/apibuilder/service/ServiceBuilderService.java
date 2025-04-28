package com.datajoy.admin_builder.apibuilder.service;

import com.datajoy.admin_builder.apibuilder.function.FunctionExecutor;
import com.datajoy.admin_builder.apibuilder.function.FunctionFactory;
import com.datajoy.admin_builder.apibuilder.function.FunctionResult;
import com.datajoy.admin_builder.apibuilder.function.ServiceFunction;
import com.datajoy.admin_builder.apibuilder.function.code.ResultCode;
import com.datajoy.admin_builder.apibuilder.message.RequestMessage;
import com.datajoy.admin_builder.apibuilder.message.ResponseMessage;
import com.datajoy.admin_builder.apibuilder.security.AuthenticatedUser;
import com.datajoy.admin_builder.apibuilder.security.AuthService;
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
            user = authService.validateAuthentication(request);
        }

        List<ServiceFunction> serviceFunctions = serviceBuilder.getServiceFunctions();

        Map<String, List<Map<String, Object>>> contents = new HashMap<>();

        for(ServiceFunction func : serviceFunctions) {
            FunctionExecutor executor = functionFactory.instance(func.getFunctionType());

            List<Map<String, Object>> params = requestMessage.getBody().get(func.getRequestMessageId());

            FunctionResult result = executor.execute(user, func.getFunctionName(), params);
            if(ResultCode.FAILURE.equals(result.getResultCode())) {
                //TODO resolved
            }

            contents.put(func.getResponseMessageId(), result.getResults());
        }

        return ResponseMessage.createSuccessMessage(contents);
    }
}
