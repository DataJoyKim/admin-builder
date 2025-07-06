package com.datajoy.admin_builder.function;

import com.datajoy.admin_builder.security.AuthenticatedUser;

import java.util.List;
import java.util.Map;

public interface FunctionExecutor {
    FunctionResult execute(AuthenticatedUser user, String functionName, List<Map<String, Object>> params);
}
