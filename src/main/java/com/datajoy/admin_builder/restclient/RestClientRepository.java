package com.datajoy.admin_builder.restclient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestClientRepository extends JpaRepository<RestClient, Long> {
    Optional<RestClient> findByClientName(String clientName);
}
