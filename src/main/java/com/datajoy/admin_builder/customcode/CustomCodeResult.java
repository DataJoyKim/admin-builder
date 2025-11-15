package com.datajoy.admin_builder.customcode;

import com.datajoy.admin_builder.customcode.code.CustomCodeResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder @AllArgsConstructor
public class CustomCodeResult {
    private CustomCodeResultCode resultCode;
    private Object content;
    private String errorMessage;

    public static CustomCodeResult createSuccessMessage(Object content) {
        return CustomCodeResult.builder()
                .resultCode(CustomCodeResultCode.SUCCESS)
                .content(content)
                .build();
    }

    public static CustomCodeResult createErrorMessage(String message) {
        return CustomCodeResult.builder()
                .resultCode(CustomCodeResultCode.FAILURE)
                .errorMessage(message)
                .build();
    }
}
