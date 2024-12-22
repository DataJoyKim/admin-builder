package com.datajoy.admin_builder.apibuilder.service.function;

import com.datajoy.admin_builder.apibuilder.entity.EntityParameter;
import com.datajoy.admin_builder.apibuilder.entity.EntityResult;
import com.datajoy.admin_builder.apibuilder.entity.EntityService;
import com.datajoy.admin_builder.apibuilder.message.MessageConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityExecutor implements FunctionExecutor {
    private final EntityService entityService;

    @Override
    public FunctionResult execute(String functionName, Object params) {
        EntityParameter entityParams = EntityParameter.builder()
                                        .contents(MessageConvert.toArray(params))
                                        .build();

        EntityResult results = entityService.execute(functionName, entityParams);

        return FunctionResult.builder()
                .results(results.getResults())
                .build();
    }
}
