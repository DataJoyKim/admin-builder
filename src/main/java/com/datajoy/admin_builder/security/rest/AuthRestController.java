package com.datajoy.admin_builder.security.rest;

import com.datajoy.admin_builder.security.AuthService;
import com.datajoy.admin_builder.security.AuthTokenResponse;
import com.datajoy.admin_builder.security.SecurityBusinessException;
import com.datajoy.admin_builder.security.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    @Autowired
    AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) throws SecurityBusinessException {
        AuthTokenResponse token = authService.refreshAccessToken(TokenUtil.resolveRefreshToken(httpRequest));

        TokenUtil.setAuthToken(httpResponse, token.getAccessToken(), token.getRefreshToken());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
