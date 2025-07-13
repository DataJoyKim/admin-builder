package com.datajoy.admin_builder.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenStoreRepository extends JpaRepository<RefreshTokenStore, Long> {
    Optional<RefreshTokenStore> findByUserId(Long userId);
}
