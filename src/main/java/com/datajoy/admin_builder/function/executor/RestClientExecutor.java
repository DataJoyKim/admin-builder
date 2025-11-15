package com.datajoy.admin_builder.function.executor;

import com.datajoy.admin_builder.function.FunctionConfig;
import com.datajoy.admin_builder.function.FunctionExecutor;
import com.datajoy.admin_builder.function.FunctionResult;
import com.datajoy.admin_builder.function.code.ResultType;
import com.datajoy.admin_builder.restclient.RestClientRequest;
import com.datajoy.admin_builder.restclient.RestClientResult;
import com.datajoy.admin_builder.restclient.RestClientService;
import com.datajoy.admin_builder.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("function.RestClientExecutor")
@RequiredArgsConstructor
public class RestClientExecutor implements FunctionExecutor {
    private final RestClientService restClientService;
    private final FunctionConfig config;

    @Override
    public FunctionResult execute(AuthenticatedUser user, String functionName, List<Map<String, Object>> params) {

        ResultType resultType = ResultType.SUCCESS;
        List<Map<String, Object>> results = new ArrayList<>();

        for(Map<String, Object> param : params) {
            String seq = (String) param.get(config.getRequestMessageSeqKey());

            RestClientRequest restClientParams = RestClientRequest.builder()
                    .params(param)
                    .requestBody(param.get(config.getRequestMessageRestClientRequestBodyKey()))
                    .build();

            RestClientResult restClientResult = restClientService.execute(functionName, restClientParams);

            if(restClientResult.getStatusCode().isError()) {
                resultType = ResultType.FAILURE;
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
                .resultType(resultType)
                .results(results)
                .build();
    }
}
