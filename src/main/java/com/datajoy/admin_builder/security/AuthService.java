package com.datajoy.admin_builder.security;

import com.datajoy.admin_builder.user.User;
import com.datajoy.admin_builder.user.UserGroupUser;
import com.datajoy.admin_builder.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final RefreshTokenStoreRepository refreshTokenStoreRepository;
    private final UserGroupAuthorityRepository userGroupAuthorityRepository;
    private final JwtProvider jwtProvider;

    public AuthenticatedUser validateAuthentication(String accessToken) throws SecurityBusinessException {
        if(accessToken == null) {
            throw new SecurityBusinessException(SecurityErrorMessage.NOT_LOGIN);
        }

        jwtProvider.validateToken(accessToken);

        AuthenticatedUser authenticatedUser = jwtProvider.parseAccessToken(accessToken);

        List<UserGroupUser> userGroupUsers = userService.getUserGroupUser(authenticatedUser.getUserId());

        for(UserGroupUser userGroupUser : userGroupUsers) {
            List<UserGroupAuthority> userGroupAuthorities = userGroupAuthorityRepository.findByUserGroupCode(userGroupUser.getUserGroup().getCode());

            for(UserGroupAuthority userGroupAuthority : userGroupAuthorities) {
                authenticatedUser.grantAuthority(userGroupAuthority.getAuthority());
            }
        }

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
