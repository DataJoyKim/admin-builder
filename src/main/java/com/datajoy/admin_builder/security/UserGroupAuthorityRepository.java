package com.datajoy.admin_builder.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupAuthorityRepository extends JpaRepository<UserGroupAuthority, Long> {
    List<UserGroupAuthority> findByUserGroupCode(String code);
}
