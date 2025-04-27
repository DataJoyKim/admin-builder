package com.datajoy.admin_builder.apibuilder.function;

import com.datajoy.admin_builder.apibuilder.security.AuthenticatedUser;
import com.datajoy.admin_builder.apibuilder.user.User;

import java.util.List;
import java.util.Map;

public interface FunctionExecutor {
    FunctionResult execute(AuthenticatedUser user, String functionName, List<Map<String, Object>> params);
}
