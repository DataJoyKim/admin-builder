package com.datajoy.admin_builder.apibuilder.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
public class QueryRequest {
    private Map<String, Object> contents;
}
