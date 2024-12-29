package com.datajoy.admin_builder.apibuilder.function;

import com.datajoy.admin_builder.apibuilder.function.code.FunctionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FunctionFactory {

    private final EntityExecutor entityExecutor;
    private final QueryExecutor queryExecutor;

    public FunctionExecutor instance(FunctionType functionType) {
        if(FunctionType.ENTITY.equals(functionType)){
            return entityExecutor;
        }
        else if(FunctionType.SQL.equals(functionType)){
            return queryExecutor;
        }
        else {
            return null;
        }
    }
}
