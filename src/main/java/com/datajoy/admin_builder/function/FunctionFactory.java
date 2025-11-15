package com.datajoy.admin_builder.function;

import com.datajoy.admin_builder.function.code.FunctionType;
import com.datajoy.admin_builder.function.executor.CustomCodeExecutor;
import com.datajoy.admin_builder.function.executor.EntityExecutor;
import com.datajoy.admin_builder.function.executor.QueryExecutor;
import com.datajoy.admin_builder.function.executor.RestClientExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FunctionFactory {

    private final EntityExecutor entityExecutor;
    private final QueryExecutor queryExecutor;
    private final RestClientExecutor restClientExecutor;
    private final CustomCodeExecutor customCodeExecutor;

    public FunctionExecutor instance(FunctionType functionType) {
        return switch (functionType) {
            case ENTITY -> entityExecutor;
            case SQL -> queryExecutor;
            case REST_CLIENT -> restClientExecutor;
            case CODE -> customCodeExecutor;
        };
    }
}
