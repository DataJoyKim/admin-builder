package com.datajoy.admin_builder.flowbuilder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceBuilderRepository extends JpaRepository<ServiceBuilder, Long> {
    Optional<ServiceBuilder> findByServiceName(String serviceName);
}
