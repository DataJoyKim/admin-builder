package com.datajoy.admin_builder.apibuilder.function;

import com.datajoy.admin_builder.apibuilder.message.MessageConvert;
import com.datajoy.admin_builder.apibuilder.query.QueryRequest;
import com.datajoy.admin_builder.apibuilder.query.QueryResult;
import com.datajoy.admin_builder.apibuilder.query.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class QueryExecutor implements FunctionExecutor {
    private final QueryService queryService;

    @Override
    public FunctionResult execute(String functionName, Object params) {
        List<Map<String, Object>> paramsArr = MessageConvert.toArray(params);

        QueryRequest queryParams = QueryRequest.builder()
                .contents(paramsArr.get(0))
                .build();

        QueryResult results = queryService.execute(functionName, queryParams);

        return FunctionResult.builder()
                .results(results.getResults())
                .build();
    }
}
