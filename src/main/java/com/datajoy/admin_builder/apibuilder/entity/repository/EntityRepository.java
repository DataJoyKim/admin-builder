package com.datajoy.admin_builder.apibuilder.entity.repository;

import com.datajoy.admin_builder.apibuilder.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntityRepository extends JpaRepository<Entity, Long> {
    Optional<Entity> findByEntityName(String entityName);
}
