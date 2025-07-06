package com.datajoy.admin_builder.user;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String loginId;
    private String userName;
    private String password;
    private String checkPassword;
    private String email;
}
