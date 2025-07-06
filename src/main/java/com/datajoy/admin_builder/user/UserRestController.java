package com.datajoy.admin_builder.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/signup")
public class UserRestController {
    @Autowired
    UserService userService;

    @PostMapping("")
    public ResponseEntity<?> signUp(
            HttpServletRequest httpRequest,
            @RequestBody SignUpRequest signUpRequest
    ) {
        User user = userService.signUp(signUpRequest);

        SignUpResponse response = SignUpResponse.of(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
