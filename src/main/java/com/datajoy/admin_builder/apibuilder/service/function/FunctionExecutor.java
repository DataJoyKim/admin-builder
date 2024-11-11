package com.datajoy.admin_builder.apibuilder.service.function;

public interface FunctionExecutor {
    FunctionResult execute(String functionName, Object params);
}
