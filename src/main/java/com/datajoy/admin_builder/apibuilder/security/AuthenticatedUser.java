package com.datajoy.admin_builder.apibuilder.security;

import com.datajoy.admin_builder.apibuilder.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @AllArgsConstructor @Builder
public class AuthenticatedUser {
    private Long userId;
    private String userName;
    private LocalDateTime lastLoginDatetime;

    public static AuthenticatedUser createAuthenticatedUser(User user) {
        return AuthenticatedUser.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .build();
    }
}
