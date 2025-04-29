package com.datajoy.admin_builder.apibuilder.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="REFRESH_TOKEN_STORE_UQ",columnNames={"USER_ID"})})
public class RefreshTokenStore {
    @Column(nullable = false, length = 100)
    private Long userId;
    private String refreshToken;

    public static RefreshTokenStore createRefreshTokenStore(Long userId, String refreshToken) {
        return RefreshTokenStore.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .build();
    }
}
