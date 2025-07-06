package com.datajoy.admin_builder.security;

import jakarta.servlet.http.HttpServletRequest;

public class TokenUtil {

    public static String resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null) {
            return null;
        }

        if(authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }
}
