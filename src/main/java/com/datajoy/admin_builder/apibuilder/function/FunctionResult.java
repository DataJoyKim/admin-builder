package com.datajoy.admin_builder.apibuilder.function;

import com.datajoy.admin_builder.apibuilder.function.code.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor @Builder
public class FunctionResult {
    private ResultCode resultCode;
    private Object results;
}
