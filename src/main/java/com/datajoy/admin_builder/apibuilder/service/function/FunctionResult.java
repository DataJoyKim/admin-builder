package com.datajoy.admin_builder.apibuilder.service.function;

import com.datajoy.admin_builder.apibuilder.service.code.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor @Builder
public class FunctionResult {
    private ResultCode resultCode;
    private Object results;
}
