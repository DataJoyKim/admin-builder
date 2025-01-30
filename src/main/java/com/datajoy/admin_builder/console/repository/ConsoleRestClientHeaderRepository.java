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
    @Query(" insert RestClientHeader a (clientId,key,value)" +
            " values ( " +
                ":clientId," +
                ":key," +
                ":value" +
            ")")
    void insert(
            @Param("clientId") Long clientId,
            @Param("key") String key,
            @Param("value") String value
    );

    @Transactional
    @Modifying
    @Query(" update RestClientHeader a " +
            " set " +
                "a.key = :key," +
                "a.value = :value" +
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("key") String key,
            @Param("value") String value
    );

    List<RestClientHeader> findByClientId(Long clientId);
}
