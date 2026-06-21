package com.datajoy.admin_builder.function.executor;

import com.datajoy.admin_builder.message.MessageProcessorRequest;
import com.datajoy.admin_builder.message.MessageProcessorResult;
import com.datajoy.admin_builder.message.MessageProcessorService;
import com.datajoy.admin_builder.message.code.MessageProcessorResultCode;
import com.datajoy.admin_builder.function.FunctionConfig;
import com.datajoy.admin_builder.function.FunctionExecutor;
import com.datajoy.admin_builder.function.FunctionResult;
import com.datajoy.admin_builder.function.code.ResultType;
import com.datajoy.admin_builder.security.domain.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageProcessorExecutor implements FunctionExecutor {
    private final MessageProcessorService messageProcessorService;
    private final FunctionConfig config;

    @Override
    public FunctionResult execute(AuthenticatedUser user, String functionName, List<Map<String, Object>> params) {

        MessageProcessorRequest request = MessageProcessorRequest.builder()
                .contents(params)
                .build();

        MessageProcessorResult result = messageProcessorService.execute(functionName, request);

        List<Map<String,Object>> results = new ArrayList<>();
        Object resultObj = result.getContent();
        if(resultObj instanceof List) {
            List<Map<String, Object>> responseArr = (List<Map<String, Object>>) resultObj;
            for(Map<String,Object> responseObj : responseArr) {
                responseObj.put(config.getRequestMessageSeqKey(), null);
            }
            results.addAll(responseArr);
        }
        else if(resultObj instanceof Map) {
            Map<String, Object> responseObj = (Map<String, Object>) resultObj;
            responseObj.put(config.getRequestMessageSeqKey(), null);
            results.add(responseObj);
        }

        return FunctionResult.builder()
                .resultType(MessageProcessorResultCode.SUCCESS.equals(result.getResultCode()) ? ResultType.SUCCESS : ResultType.FAILURE)
                .results(results)
                .build();
    }
}
