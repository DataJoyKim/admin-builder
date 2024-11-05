package com.datajoy.web_builder.apibuilder.command;

import com.datajoy.web_builder.apibuilder.api.ApiBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class BusinessCommandExecutor {
    private final ApplicationContext applicationContext;
    public Object execute(ApiBuilder apiBuilder, Map<String, Object> requestParams, Object requestBody) {
        BusinessCommand businessCommand = (BusinessCommand) applicationContext.getBean(apiBuilder.getCommandBeanName());

        return null;
    }
}
