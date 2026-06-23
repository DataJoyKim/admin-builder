package com.datajoy.admin_builder.security.repository;

import com.datajoy.admin_builder.security.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
