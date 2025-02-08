package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.apibuilder.restclient.RestClientHeader;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsoleRestClientHeaderRepository extends JpaRepository<RestClientHeader, Long> {
    @Transactional
    @Modifying
    @Query(" insert RestClientHeader a (clientId,name,headerValue)" +
            " values ( " +
                ":clientId," +
                ":name," +
                ":headerValue" +
            ")")
    void insert(
            @Param("clientId") Long clientId,
            @Param("name") String name,
            @Param("headerValue") String headerValue
    );

    @Transactional
    @Modifying
    @Query(" update RestClientHeader a " +
            " set " +
                "a.name = :name," +
                "a.headerValue = :headerValue" +
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("headerValue") String headerValue
    );

    List<RestClientHeader> findByClientId(Long clientId);
}
