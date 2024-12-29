package com.datajoy.admin_builder.apibuilder.function;

public interface FunctionExecutor {
    FunctionResult execute(String functionName, Object params);
}
