package com.datajoy.admin_builder.security.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor @Builder
public class AuthTokenResponse {
    private String accessToken;
    private String refreshToken;
}
