package com.datajoy.admin_builder.apibuilder.query;

import com.datajoy.admin_builder.apibuilder.query.code.QueryResultCode;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
@Getter
@Builder
public class QueryResult {
    private QueryResultCode resultCode;
    private List<Map<String, Object>> results;
    public static QueryResult createQueryResult(QueryResultCode resultCode, List<Map<String, Object>> results) {
        return QueryResult.builder()
                .results(results)
                .resultCode(resultCode)
                .build();
    }
}
