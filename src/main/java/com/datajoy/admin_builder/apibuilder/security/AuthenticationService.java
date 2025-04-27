package com.datajoy.admin_builder.apibuilder.security;

import com.datajoy.admin_builder.apibuilder.user.User;
import com.datajoy.admin_builder.apibuilder.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final SecurityConfig config;

    public AuthenticatedUser validateAuthentication(HttpServletRequest request) {
        String accessToken = TokenUtil.getAccessToken(request);

        JwtToken jwtToken = new JwtToken(config.getTokenJwtSecretKey());

        jwtToken.validateToken(accessToken);

        AuthenticatedUser authenticatedUser = jwtToken.parseToken(accessToken);

        //TODO 권한 셋팅

        return authenticatedUser;
    }

    public void validateAuthorization(AuthenticatedUser user) {
        //TODO 권한 검증
    }

    public AuthenticatedToken authenticate(LoginRequest loginRequest) {

        User user = userService.getUserByLoginId(loginRequest.getLoginId());
        if(user == null) {
            //TODO Exception
        }

        if(!loginRequest.getPassword().equals(user.getPassword())) {
            //TODO Exception
        }

        //TODO 권한 셋팅
        AuthenticatedUser authenticatedUser = AuthenticatedUser.createAuthenticatedUser(user);

        // 토큰 생성
        JwtToken jwtToken = new JwtToken(config.getTokenJwtSecretKey());

        String accessToken = jwtToken.generateToken(authenticatedUser, config.getTokenAccessTokenExpireTime());

        return AuthenticatedToken.builder()
                .accessToken(accessToken)
                .build();
    }
}
