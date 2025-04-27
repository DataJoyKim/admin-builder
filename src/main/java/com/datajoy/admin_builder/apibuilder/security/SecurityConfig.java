package com.datajoy.admin_builder.apibuilder.security;

import lombok.Getter;

@Getter
public class SecurityConfig {
    private String tokenJwtSecretKey = "";
    private Long tokenAccessTokenExpireTime = 100000L;
}
