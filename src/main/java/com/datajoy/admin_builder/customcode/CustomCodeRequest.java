package com.datajoy.admin_builder.customcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter @AllArgsConstructor @Builder
public class CustomCodeRequest {
    private List<Map<String, Object>> contents;
}
