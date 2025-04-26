package com.datajoy.admin_builder.apibuilder.security;

import com.datajoy.admin_builder.apibuilder.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public User validateAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null) {
            return null;
        }

        if(authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.substring(7);
        }

        return null;
    }

    public AuthenticatedToken authenticate(LoginRequest loginRequest) {

        return null;
    }
}
