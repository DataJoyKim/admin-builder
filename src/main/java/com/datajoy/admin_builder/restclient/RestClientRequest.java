package com.datajoy.admin_builder.restclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Builder
@Getter
public class RestClientRequest {
    private Map<String, Object> params;
    private Object requestBody;
}
