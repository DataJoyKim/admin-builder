package com.datajoy.admin_builder.apibuilder.function;

import java.util.List;
import java.util.Map;

public interface FunctionExecutor {
    FunctionResult execute(String functionName, List<Map<String, Object>> params);
}
