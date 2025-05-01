package com.datajoy.admin_builder.apibuilder.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refresh(
            HttpServletRequest httpRequest
    ) throws SecurityBusinessException {
        AuthTokenResponse token = authService.refreshAccessToken(TokenUtil.resolveToken(httpRequest));

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
