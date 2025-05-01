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

    public AuthenticatedUser validateAuthentication(String accessToken) throws SecurityBusinessException {
        jwtProvider.validateToken(accessToken);

        AuthenticatedUser authenticatedUser = jwtProvider.parseAccessToken(accessToken);

        //TODO 권한 셋팅

        return authenticatedUser;
    }

    public void validateAuthorization(AuthenticatedUser user) {
        //TODO 권한 검증
    }

    public AuthTokenResponse refreshAccessToken(String refreshToken) throws SecurityBusinessException {
        jwtProvider.validateToken(refreshToken);

        Long userId = jwtProvider.getUserIdToRefreshToken(refreshToken);

        String savedRefreshToken = refreshTokenStoreRepository.findByUserId(userId);
        if (!refreshToken.equals(savedRefreshToken)) {
            throw new SecurityBusinessException(SecurityErrorMessage.DIFF_REFRESH_TOKEN);
        }

        User user = userService.getUserByUserId(userId);

        AuthenticatedUser authenticatedUser = AuthenticatedUser.createAuthenticatedUser(user);

        String newAccessToken = jwtProvider.generateAccessToken(authenticatedUser);

        return AuthTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
