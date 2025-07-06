package com.datajoy.admin_builder.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor @Builder
public class GrantedAuthority {
    private String role;
}
