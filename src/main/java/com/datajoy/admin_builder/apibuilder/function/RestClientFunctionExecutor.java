package com.datajoy.admin_builder.apibuilder.function;

import com.datajoy.admin_builder.apibuilder.function.code.ResultCode;
import com.datajoy.admin_builder.apibuilder.restclient.RestClientRequest;
import com.datajoy.admin_builder.apibuilder.restclient.RestClientResult;
import com.datajoy.admin_builder.apibuilder.restclient.RestClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RestClientFunctionExecutor implements FunctionExecutor {
    private final RestClientService restClientService;
    private final FunctionConfig config;

    @Override
    public FunctionResult execute(String functionName, List<Map<String, Object>> params) {

        ResultCode resultCode = ResultCode.SUCCESS;
        List<Map<String, Object>> results = new ArrayList<>();

        for(Map<String, Object> param : params) {
            String seq = (String) param.get(config.getRequestMessageSeqKey());

            RestClientRequest restClientParams = RestClientRequest.builder()
                    .params(param)
                    .requestBody(param.get(config.getRequestMessageRestClientRequestBodyKey()))
                    .build();

            RestClientResult restClientResult = restClientService.execute(functionName, restClientParams);

            if(restClientResult.getStatusCode().isError()) {
                resultCode = ResultCode.FAILURE;
            }

            Object response = restClientResult.getBody();

            if(response instanceof List) {
                List<Map<String, Object>> responseArr = (List<Map<String, Object>>) response;
                for(Map<String, Object> obj : responseArr) {
                    obj.put(config.getRequestMessageSeqKey(), seq);
                }

                results.addAll(responseArr);
            }
            else if(response instanceof Map) {
                Map<String, Object> responseObj = (Map<String, Object>) response;
                responseObj.put(config.getRequestMessageSeqKey(), seq);

                results.add(responseObj);
            }
        }

        return FunctionResult.builder()
                .resultCode(resultCode)
                .results(results)
                .build();
    }
}
