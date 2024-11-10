package com.datajoy.admin_builder.apibuilder.api;

import com.datajoy.admin_builder.apibuilder.command.BusinessCommandExecutor;
import com.datajoy.admin_builder.apibuilder.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApiBuilderService {
    private final BusinessCommandExecutor businessCommandExecutor;

    public ResponseMessage<?> build(
            String applicationName,
            String path,
            HttpMethod method,
            Map<String, Object> requestParams,
            Object requestBody
    ) {
        //ApiBuilder apiBuilder = apiBuilderRepository.findBy(applicationName, path, method);

        //Object contents = businessCommandExecutor.execute(apiBuilder, requestParams, requestBody);

        return ResponseMessage.createMessage(null);
    }
}