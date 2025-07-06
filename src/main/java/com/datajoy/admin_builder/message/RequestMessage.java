package com.datajoy.admin_builder.message;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class RequestMessage {
    private Map<String, List<Map<String, Object>>> body;
}
