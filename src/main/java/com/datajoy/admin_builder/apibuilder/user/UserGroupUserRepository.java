package com.datajoy.admin_builder.apibuilder.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGroupUserRepository extends JpaRepository<UserGroupUser, Long> {
    List<UserGroupUser> findByUserId(Long userId);
}
