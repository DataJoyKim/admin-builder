package com.datajoy.admin_builder.security.controller;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String loginId;
    private String password;
}
