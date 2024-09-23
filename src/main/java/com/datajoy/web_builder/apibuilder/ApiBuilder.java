package com.datajoy.web_builder.apibuilder;

import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public class ApiBuilder {
    private Long id;
    private String applicationName;
    private String path;
    private HttpMethod method;
    private Boolean useAuth;
    private String commandBeanName;
}
