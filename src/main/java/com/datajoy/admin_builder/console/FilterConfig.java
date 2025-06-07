package com.datajoy.admin_builder.console;

import com.datajoy.admin_builder.apibuilder.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Autowired
    AuthService authService;

    @Bean
    public FilterRegistrationBean<ConsoleSecurityFilter> consoleSecurityFilterRegistration() {
        FilterRegistrationBean<ConsoleSecurityFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ConsoleSecurityFilter(authService));
        registration.addUrlPatterns("/console/*");
        registration.setOrder(1);

        return registration;
    }
}
