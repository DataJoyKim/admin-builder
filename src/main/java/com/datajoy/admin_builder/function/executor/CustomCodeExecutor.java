package com.datajoy.admin_builder.function.executor;

import com.datajoy.admin_builder.customcode.CustomCodeRequest;
import com.datajoy.admin_builder.customcode.CustomCodeResult;
import com.datajoy.admin_builder.customcode.CustomCodeService;
import com.datajoy.admin_builder.customcode.code.CustomCodeResultCode;
import com.datajoy.admin_builder.function.FunctionExecutor;
import com.datajoy.admin_builder.function.FunctionResult;
import com.datajoy.admin_builder.function.code.ResultType;
import com.datajoy.admin_builder.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomCodeExecutor implements FunctionExecutor {
    private final CustomCodeService customCodeService;

    @Override
    public FunctionResult execute(AuthenticatedUser user, String functionName, List<Map<String, Object>> params) {
        List<Map<String,Object>> results = new ArrayList<>();

        CustomCodeRequest request = CustomCodeRequest.builder()
                .contents(params)
                .build();

        CustomCodeResult result = customCodeService.execute(functionName, request);

        return FunctionResult.builder()
                .resultType(CustomCodeResultCode.SUCCESS.equals(result.getResultCode()) ? ResultType.SUCCESS : ResultType.FAILURE)
                .results(results)
                .build();
    }
}
