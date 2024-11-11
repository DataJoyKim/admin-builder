package com.datajoy.admin_builder.apibuilder.service.function;

import com.datajoy.admin_builder.apibuilder.service.code.FunctionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FunctionFactory {

    private final EntityExecutor entityExecutor;

    public FunctionExecutor instance(FunctionType functionType) {
        if(FunctionType.ENTITY.equals(functionType)){
            return entityExecutor;
        }
        else {
            return null;
        }
    }
}
