package com.datajoy.admin_builder.apibuilder.query;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
@Getter
@Builder
public class QueryResult {
    private List<Map<String, Object>> results;
    public static QueryResult createQueryResult(List<Map<String, Object>> results) {
        return QueryResult.builder()
                .results(results)
                .build();
    }
}
