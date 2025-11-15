package com.datajoy.admin_builder.customcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomCodeRepository extends JpaRepository<CustomCode, Long> {
    Optional<CustomCode> findByCodeName(String codeName);
}
