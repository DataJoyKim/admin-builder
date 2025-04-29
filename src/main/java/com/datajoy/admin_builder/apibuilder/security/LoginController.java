package com.datajoy.admin_builder.apibuilder.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            HttpServletRequest httpRequest,
            @RequestBody LoginRequest loginRequest
    ) throws SecurityException {
        Client client = new Client(); //TODO httpRequest create

        AuthTokenResponse token = authService.login(loginRequest, client);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refresh(
            HttpServletRequest httpRequest,
            @RequestBody LoginRequest loginRequest
    ) throws SecurityException {
        AuthTokenResponse token = authService.refreshAccessToken(null);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
