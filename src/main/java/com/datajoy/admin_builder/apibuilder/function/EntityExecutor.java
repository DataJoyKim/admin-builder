package com.datajoy.admin_builder.apibuilder.function;

import com.datajoy.admin_builder.apibuilder.entity.EntityRequest;
import com.datajoy.admin_builder.apibuilder.entity.EntityResult;
import com.datajoy.admin_builder.apibuilder.entity.EntityService;
import com.datajoy.admin_builder.apibuilder.entity.code.EntityResultCode;
import com.datajoy.admin_builder.apibuilder.function.code.ResultCode;
import com.datajoy.admin_builder.apibuilder.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EntityExecutor implements FunctionExecutor {
    private final EntityService entityService;

    @Override
    public FunctionResult execute(User user, String functionName, List<Map<String, Object>> params) {
        EntityRequest entityParams = EntityRequest.builder()
                                        .contents(params)
                                        .build();

        EntityResult results = entityService.execute(functionName, entityParams);

        return FunctionResult.builder()
                .resultCode(EntityResultCode.SUCCESS.equals(results.getResultCode()) ? ResultCode.SUCCESS : ResultCode.FAILURE)
                .results(results.getResults())
                .build();
    }
}
