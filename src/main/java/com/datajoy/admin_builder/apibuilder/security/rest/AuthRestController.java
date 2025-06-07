package com.datajoy.admin_builder.apibuilder.security.rest;

import com.datajoy.admin_builder.apibuilder.security.AuthService;
import com.datajoy.admin_builder.apibuilder.security.AuthTokenResponse;
import com.datajoy.admin_builder.apibuilder.security.SecurityBusinessException;
import com.datajoy.admin_builder.apibuilder.security.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {
    @Autowired
    AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            HttpServletRequest httpRequest
    ) throws SecurityBusinessException {
        AuthTokenResponse token = authService.refreshAccessToken(TokenUtil.resolveToken(httpRequest));

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
