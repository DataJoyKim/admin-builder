package com.datajoy.admin_builder.security.rest;

import com.datajoy.admin_builder.security.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginRestController {
    @Autowired
    LoginService loginService;

    @PostMapping("")
    public ResponseEntity<?> login(
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse,
            @RequestBody LoginRequest loginRequest
    ) throws SecurityBusinessException {
        Client client = new Client(httpRequest);

        AuthTokenResponse token = loginService.login(loginRequest, client);

        TokenUtil.setAuthToken(httpResponse, token.getAccessToken(), token.getRefreshToken());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
