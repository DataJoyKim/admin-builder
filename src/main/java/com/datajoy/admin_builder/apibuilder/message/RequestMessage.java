package com.datajoy.admin_builder.apibuilder.message;

import lombok.Getter;

import java.util.Map;

@Getter
public class RequestMessage {
    private Map<String, Object> body;
}
