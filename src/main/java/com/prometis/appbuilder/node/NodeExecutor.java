package com.prometis.appbuilder.node;

import com.prometis.appbuilder.security.domain.AuthenticatedUser;

import java.util.List;
import java.util.Map;

public interface NodeExecutor {
    NodeResult execute(AuthenticatedUser user, String functionName, List<Map<String, Object>> params);
}
