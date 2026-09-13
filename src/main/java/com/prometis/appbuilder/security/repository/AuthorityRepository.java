package com.prometis.appbuilder.security.repository;

import com.prometis.appbuilder.security.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
