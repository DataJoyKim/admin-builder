package com.datajoy.web_builder.apibuilder.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class ApiBuilder {
    private Long id;
    private String applicationName;
    private String path;
    private String method;
    private Boolean useAuth;
    private String commandBeanName;
}
