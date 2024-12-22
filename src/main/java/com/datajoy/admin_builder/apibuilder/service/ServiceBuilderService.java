package com.datajoy.admin_builder.apibuilder.service;

import com.datajoy.admin_builder.apibuilder.message.RequestMessage;
import com.datajoy.admin_builder.apibuilder.message.ResponseMessage;
import com.datajoy.admin_builder.apibuilder.service.code.ResultCode;
import com.datajoy.admin_builder.apibuilder.service.function.FunctionExecutor;
import com.datajoy.admin_builder.apibuilder.service.function.FunctionFactory;
import com.datajoy.admin_builder.apibuilder.service.function.FunctionResult;
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

    public ResponseMessage execute(String serviceName, RequestMessage requestMessage) {
        ServiceBuilder serviceBuilder = serviceRepository.findByServiceName(serviceName)
                                            .orElseThrow();

        List<ServiceFunction> serviceFunctions = serviceBuilder.getServiceFunctions();

        Map<String, Object> contents = new HashMap<>();

        for(ServiceFunction func : serviceFunctions) {
            FunctionExecutor executor = functionFactory.instance(func.getFunctionType());

            Object params = requestMessage.getBody().get(func.getRequestMessageId());

            FunctionResult result = executor.execute(func.getFunctionName(), params);
            if(ResultCode.ERROR.equals(result.getResultCode())) {
                //TODO resolved
            }

            contents.put(func.getResponseMessageId(), result.getResults());
        }

        return ResponseMessage.createSuccessMessage(contents);
    }
}
