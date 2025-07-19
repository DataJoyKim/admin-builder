package com.datajoy.admin_builder.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor @Builder
public class GrantedAuthority {
    private String role;

    public static boolean hasAuthority(List<GrantedAuthority> grantedAuthorities, String authorityCode) {
        for(GrantedAuthority grantedAuthority : grantedAuthorities) {
            if (authorityCode.equals(grantedAuthority.getRole())) {
                return true;
            }
        }

        return false;
    }
}
