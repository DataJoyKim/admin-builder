package com.datajoy.admin_builder.function.executor;

import com.datajoy.admin_builder.datasource.notification.SendResultType;
import com.datajoy.admin_builder.function.FunctionConfig;
import com.datajoy.admin_builder.function.FunctionExecutor;
import com.datajoy.admin_builder.function.FunctionResult;
import com.datajoy.admin_builder.function.code.ResultType;
import com.datajoy.admin_builder.notification.NotificationRequest;
import com.datajoy.admin_builder.notification.NotificationResult;
import com.datajoy.admin_builder.notification.NotificationService;
import com.datajoy.admin_builder.security.domain.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationExecutor implements FunctionExecutor {
    private final NotificationService notificationService;
    private final FunctionConfig config;

    @Override
    public FunctionResult execute(AuthenticatedUser user, String functionName, List<Map<String, Object>> params) {

        ResultType resultType = ResultType.SUCCESS;
        List<Map<String, Object>> results = new ArrayList<>();

        for(Map<String, Object> param : params) {
            String seq = (String) param.get(config.getRequestMessageSeqKey());

            NotificationRequest notificationRequest = NotificationRequest.builder()
                    .params(param)
                    .build();

            NotificationResult result = notificationService.execute(functionName, notificationRequest);

            if(result.getResultCode() == SendResultType.FAILURE) {
                resultType = ResultType.FAILURE;
            }

            Map<String, Object> responseObj = new HashMap<>();
            responseObj.put(config.getRequestMessageSeqKey(), seq);
            responseObj.put("message", result.getMessage());
            responseObj.put("resultCode", result.getResultCode());

            results.add(responseObj);
        }

        return FunctionResult.builder()
                .resultType(resultType)
                .results(results)
                .build();
    }
}
