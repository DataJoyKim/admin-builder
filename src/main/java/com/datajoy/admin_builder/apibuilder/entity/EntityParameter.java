package com.datajoy.admin_builder.apibuilder.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter @AllArgsConstructor @Builder
public class EntityParameter {
    private List<Map<String, Object>> contents;
}
