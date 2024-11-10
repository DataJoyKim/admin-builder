package com.datajoy.admin_builder.apibuilder.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class EntityParameter {
    private List<Map<String, Object>> contents = new ArrayList<>();
}
