package com.datajoy.admin_builder.apibuilder.security;

import com.datajoy.admin_builder.apibuilder.user.User;
import com.datajoy.admin_builder.apibuilder.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final SecurityConfig config;

    public AuthenticatedUser validateAuthentication(HttpServletRequest request) {
        String accessToken = TokenUtil.getAccessToken(request);

        JwtProvider jwtProvider = new JwtProvider(config.getTokenJwtSecretKey());

        jwtProvider.validateToken(accessToken);

        AuthenticatedUser authenticatedUser = jwtProvider.parseToken(accessToken);

        //TODO 권한 셋팅

        return authenticatedUser;
    }

    public void validateAuthorization(AuthenticatedUser user) {
        //TODO 권한 검증
    }

    public AuthenticatedToken authenticate(LoginRequest loginRequest) throws SecurityException {

        User user = userService.getUserByLoginId(loginRequest.getLoginId());
        if(user == null) {
            throw new SecurityException();
        }

        if(!loginRequest.getPassword().equals(user.getPassword())) {
            throw new SecurityException();
        }

        AuthenticatedUser authenticatedUser = AuthenticatedUser.createAuthenticatedUser(user);

        // 토큰 생성
        JwtProvider jwtProvider = new JwtProvider(config.getTokenJwtSecretKey());

        String accessToken = jwtProvider.generateToken(authenticatedUser, config.getTokenAccessTokenExpireTime());

        return AuthenticatedToken.builder()
                .accessToken(accessToken)
                .build();
    }
}
