package com.datajoy.admin_builder.security.service;

import com.datajoy.admin_builder.security.domain.*;
import com.datajoy.admin_builder.security.exception.SecurityBusinessException;
import com.datajoy.admin_builder.security.exception.SecurityErrorMessage;
import com.datajoy.admin_builder.security.token.AuthTokenResponse;
import com.datajoy.admin_builder.security.token.JwtProvider;
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

    public AuthenticatedUser authentication(String accessToken) throws SecurityBusinessException {
        if(accessToken == null) {
            throw new SecurityBusinessException(SecurityErrorMessage.NOT_LOGIN);
        }

        // 토큰 유효성검사
        jwtProvider.validateToken(accessToken);

        // 사용자 정보 조회
        AuthenticatedUser authenticatedUser = jwtProvider.parseAccessToken(accessToken);
        
        // 사용자 권한 부여
        List<UserGroupUser> userGroupUsers = userService.getUserGroupUser(authenticatedUser.getUserId());

        for(UserGroupUser userGroupUser : userGroupUsers) {
            List<UserGroupAuthority> userGroupAuthorities = userGroupAuthorityRepository.findByUserGroupCode(userGroupUser.getUserGroup().getCode());

            for(UserGroupAuthority userGroupAuthority : userGroupAuthorities) {
                authenticatedUser.grantAuthority(userGroupAuthority.getAuthority());
            }
        }

        return authenticatedUser;
    }

    public AuthTokenResponse refreshToken(String refreshToken) throws SecurityBusinessException {
        jwtProvider.validateToken(refreshToken);

        Long userId = jwtProvider.getUserIdToRefreshToken(refreshToken);

        RefreshTokenStore savedRefreshTokenStore = refreshTokenStoreRepository.findByUserId(userId)
                                                        .orElseThrow();

        if (!refreshToken.equals(savedRefreshTokenStore.getRefreshToken())) {
            throw new SecurityBusinessException(SecurityErrorMessage.DIFF_REFRESH_TOKEN);
        }

        User user = userService.getUserByUserId(userId);
        if(user == null) {
            throw new SecurityBusinessException(SecurityErrorMessage.NOT_FOUND_USER);
        }

        AuthenticatedUser authenticatedUser = AuthenticatedUser.createAuthenticatedUser(user);

        String newAccessToken = jwtProvider.generateAccessToken(authenticatedUser);

        return AuthTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
