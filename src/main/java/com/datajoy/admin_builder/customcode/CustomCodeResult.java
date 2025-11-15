package com.datajoy.admin_builder.customcode;

import com.datajoy.admin_builder.customcode.code.CustomCodeResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Builder @AllArgsConstructor
public class CustomCodeResult {
    private CustomCodeResultCode resultCode;
    private Object content;

    public static CustomCodeResult createSuccessMessage(Object content) {
        return CustomCodeResult.builder()
                .resultCode(CustomCodeResultCode.SUCCESS)
                .content(content)
                .build();
    }

    public static CustomCodeResult createErrorMessage(String errorCode, String errorMessage) {
        List<Map<String, Object>> resultData = new ArrayList<>();
        Map<String, Object> error = new HashMap<>();
        error.put("errorCode", errorCode);
        error.put("message",errorMessage);
        resultData.add(error);

        return CustomCodeResult.builder()
                .resultCode(CustomCodeResultCode.FAILURE)
                .content(resultData)
                .build();
    }
}
