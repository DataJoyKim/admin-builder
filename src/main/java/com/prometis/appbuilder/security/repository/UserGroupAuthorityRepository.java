package com.prometis.appbuilder.security.repository;

import com.prometis.appbuilder.security.domain.UserGroupAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupAuthorityRepository extends JpaRepository<UserGroupAuthority, Long> {
    List<UserGroupAuthority> findByUserGroupCode(String code);

    List<UserGroupAuthority> findByUserGroupId(Long userGroupId);
}
