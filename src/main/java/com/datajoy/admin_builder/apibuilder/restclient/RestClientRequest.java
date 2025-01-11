package com.datajoy.admin_builder.apibuilder.restclient;

import lombok.Getter;

import java.util.Map;

@Getter
public class RestClientRequest {
    private Map<String, Object> params;
    private Object requestBody;
}
