package com.datajoy.admin_builder.function.executor;

import com.datajoy.admin_builder.function.FunctionConfig;
import com.datajoy.admin_builder.function.FunctionExecutor;
import com.datajoy.admin_builder.function.FunctionResult;
import com.datajoy.admin_builder.function.code.ResultType;
import com.datajoy.admin_builder.query.QueryRequest;
import com.datajoy.admin_builder.query.QueryResult;
import com.datajoy.admin_builder.query.QueryService;
import com.datajoy.admin_builder.security.AuthenticatedUser;
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
    public FunctionResult execute(AuthenticatedUser user, String functionName, List<Map<String, Object>> params) {
        ResultType resultType = ResultType.SUCCESS;
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
                .resultType(resultType)
                .results(results)
                .build();
    }
}
