package com.datajoy.admin_builder.apibuilder.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityBeanConfig {
    @Autowired
    private SecurityProperties config;

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(config.getTokenJwtSecretKey(), config.getTokenAccessTokenExpireTime(), config.getTokenRefreshTokenExpireTime());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
