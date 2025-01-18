package com.datajoy.admin_builder.apibuilder.function;

import com.datajoy.admin_builder.apibuilder.function.code.ResultCode;
import com.datajoy.admin_builder.apibuilder.query.QueryRequest;
import com.datajoy.admin_builder.apibuilder.query.QueryResult;
import com.datajoy.admin_builder.apibuilder.query.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class QueryExecutor implements FunctionExecutor {
    private final QueryService queryService;
    private final FunctionConfig config;

    @Override
    public FunctionResult execute(String functionName, List<Map<String, Object>> params) {
        ResultCode resultCode = ResultCode.SUCCESS;
        List<Map<String,Object>> results = new ArrayList<>();

        for(Map<String, Object> param : params) {
            String seq = (String) param.get(config.getRequestMessageSeqKey());

            QueryRequest queryParams = QueryRequest.builder()
                                        .contents(param)
                                        .build();

            QueryResult queryResults = queryService.execute(functionName, queryParams);

            for(Map<String,Object> result : queryResults.getResults()) {
                result.put(config.getRequestMessageSeqKey(), seq);
            }

            results.addAll(queryResults.getResults());
        }

        return FunctionResult.builder()
                .resultCode(resultCode)
                .results(results)
                .build();
    }
}
