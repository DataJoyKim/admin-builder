package com.datajoy.admin_builder.security;

import com.datajoy.admin_builder.user.User;
import com.datajoy.admin_builder.user.UserService;
import com.datajoy.core.crypto.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenStoreRepository refreshTokenStoreRepository;

    public AuthTokenResponse login(LoginRequest loginRequest, Client client) throws SecurityBusinessException {

        User user = userService.getUserByLoginId(loginRequest.getLoginId());
        if(user == null) {
            throw new SecurityBusinessException(SecurityErrorMessage.NOT_FOUND_USER);
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())) {
            throw new SecurityBusinessException(SecurityErrorMessage.FAULT_PASSWORD);
        }

        AuthenticatedUser authenticatedUser = AuthenticatedUser.createAuthenticatedUser(user);

        String accessToken = jwtProvider.generateAccessToken(authenticatedUser);

        String refreshToken = jwtProvider.generateRefreshToken(authenticatedUser, client);

        RefreshTokenStore store = RefreshTokenStore.createRefreshTokenStore(user.getId(), refreshToken);

        refreshTokenStoreRepository.save(store);

        return AuthTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
