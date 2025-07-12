package com.datajoy.admin_builder;

import com.datajoy.admin_builder.security.JwtProvider;
import com.datajoy.admin_builder.security.SecurityProperties;
import com.datajoy.core.crypto.BCryptPasswordEncoder;
import com.datajoy.core.crypto.PasswordEncoder;
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
