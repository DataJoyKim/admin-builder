package com.datajoy.admin_builder.apibuilder.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenStoreRepository extends JpaRepository<RefreshTokenStore, Long> {
    String findByUserId(Long userId);
}
