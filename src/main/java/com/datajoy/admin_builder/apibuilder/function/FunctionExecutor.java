package com.datajoy.admin_builder.apibuilder.function;

import com.datajoy.admin_builder.apibuilder.account.User;

import java.util.List;
import java.util.Map;

public interface FunctionExecutor {
    FunctionResult execute(User user, String functionName, List<Map<String, Object>> params);
}
