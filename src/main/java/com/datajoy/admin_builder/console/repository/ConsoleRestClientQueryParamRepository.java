package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.apibuilder.restclient.RestClientQueryParam;
import com.datajoy.admin_builder.apibuilder.restclient.code.AutoValueType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsoleRestClientQueryParamRepository extends JpaRepository<RestClientQueryParam, Long> {
    @Transactional
    @Modifying
    @Query(" insert RestClientQueryParam a (clientId,paramName,autoValueType)" +
            " values ( " +
                ":clientId," +
                ":paramName," +
                ":autoValueType" +
            ")")
    void insert(
            @Param("clientId") Long clientId,
            @Param("paramName") String paramName,
            @Param("autoValueType") AutoValueType autoValueType
    );

    @Transactional
    @Modifying
    @Query(" update RestClientQueryParam a " +
            " set " +
                "a.paramName = :paramName," +
                "a.autoValueType = :autoValueType," +
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("paramName") String paramName,
            @Param("autoValueType") AutoValueType autoValueType
    );

    List<RestClientQueryParam> findByClientId(Long clientId);
}
