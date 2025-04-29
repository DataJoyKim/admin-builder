package com.datajoy.admin_builder.apibuilder.security;

import com.datajoy.admin_builder.apibuilder.user.User;
import com.datajoy.admin_builder.apibuilder.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final RefreshTokenStoreRepository refreshTokenStoreRepository;
    private final JwtProvider jwtProvider;

    public AuthenticatedUser validateAuthentication(String accessToken) {
        jwtProvider.validateToken(accessToken);

        AuthenticatedUser authenticatedUser = jwtProvider.parseAccessToken(accessToken);

        //TODO 권한 셋팅

        return authenticatedUser;
    }

    public void validateAuthorization(AuthenticatedUser user) {
        //TODO 권한 검증
    }

    public AuthTokenResponse refreshAccessToken(String refreshToken) throws SecurityException {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new SecurityException();
        }

        Long userId = jwtProvider.getUserIdToRefreshToken(refreshToken);

        String savedRefreshToken = refreshTokenStoreRepository.findByUserId(userId);
        if (!refreshToken.equals(savedRefreshToken)) {
            throw new SecurityException();
        }

        User user = userService.getUserByUserId(userId);

        AuthenticatedUser authenticatedUser = AuthenticatedUser.createAuthenticatedUser(user);

        String newAccessToken = jwtProvider.generateAccessToken(authenticatedUser);

        return AuthTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthTokenResponse login(LoginRequest loginRequest, Client client) throws SecurityException {

        User user = userService.getUserByLoginId(loginRequest.getLoginId());
        if(user == null) {
            throw new SecurityException();
        }

        if(!loginRequest.getPassword().equals(user.getPassword())) {
            throw new SecurityException();
        }

        AuthenticatedUser authenticatedUser = AuthenticatedUser.createAuthenticatedUser(user);

        String accessToken = jwtProvider.generateAccessToken(authenticatedUser);

        String refreshToken = jwtProvider.generateRefreshToken(authenticatedUser, client);

        RefreshTokenStore store = RefreshTokenStore.createRefreshTokenStore(user.getUserId(), refreshToken);

        refreshTokenStoreRepository.save(store);

        return AuthTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
