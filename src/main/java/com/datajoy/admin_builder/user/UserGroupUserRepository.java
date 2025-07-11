package com.datajoy.admin_builder.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupUserRepository extends JpaRepository<UserGroupUser, Long> {
    List<UserGroupUser> findByUserId(Long userId);
}
