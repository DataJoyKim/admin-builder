package com.datajoy.admin_builder.console;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ConsoleSecurityFilter> consoleSecurityFilterRegistration(ConsoleSecurityFilter consoleSecurityFilter) {
        FilterRegistrationBean<ConsoleSecurityFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(consoleSecurityFilter);
        registration.addUrlPatterns("/console/*");
        registration.setOrder(1);

        return registration;
    }
}
