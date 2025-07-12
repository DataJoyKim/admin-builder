package com.datajoy.admin_builder;

import com.datajoy.admin_builder.console.ConsoleSecurityFilter;
import com.datajoy.admin_builder.security.AuthService;
import com.datajoy.admin_builder.security.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Autowired
    AuthService authService;
    @Autowired
    SecurityProperties securityProperties;

    @Bean
    public FilterRegistrationBean<ConsoleSecurityFilter> consoleSecurityFilterRegistration() {
        FilterRegistrationBean<ConsoleSecurityFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ConsoleSecurityFilter(authService, securityProperties));
        registration.addUrlPatterns("/console/*");
        registration.setOrder(1);

        return registration;
    }
}
