package com.datajoy.admin_builder.apibuilder.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class EntityResult {
    private Map<String, List<Map<String, Object>>> results;

    public static EntityResult createEntityResult(Map<String, List<Map<String, Object>>> results) {
        return EntityResult.builder()
                .results(results)
                .build();
    }
}
