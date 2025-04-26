package com.datajoy.admin_builder.apibuilder.security;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String loginId;
    private String password;
}
