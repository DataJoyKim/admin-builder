package com.datajoy.admin_builder.function;

import com.datajoy.admin_builder.function.code.ResultType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter @AllArgsConstructor @Builder
public class FunctionResult {
    private ResultType resultType;
    private List<Map<String, Object>> results;
}
