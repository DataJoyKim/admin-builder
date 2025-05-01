package com.datajoy.admin_builder.apibuilder.security;

import lombok.Getter;

@Getter
public class SecurityProperties {
    private String tokenJwtSecretKey = "";
    private Long tokenAccessTokenExpireTime = 1000L * 60 * 15;
    private Long tokenRefreshTokenExpireTime = 1000L * 60 * 60 * 24 * 7;
}
