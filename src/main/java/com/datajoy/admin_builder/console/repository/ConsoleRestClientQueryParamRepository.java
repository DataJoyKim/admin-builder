package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.restclient.RestClientQueryParam;
import com.datajoy.admin_builder.restclient.code.ValueType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsoleRestClientQueryParamRepository extends JpaRepository<RestClientQueryParam, Long> {
    @Transactional
    @Modifying
    @Query(" insert RestClientQueryParam a (clientId,paramName,valueType, inputValue)" +
            " values ( " +
                ":clientId," +
                ":paramName," +
                ":valueType, " +
                ":inputValue" +
            ")")
    void insert(
            @Param("clientId") Long clientId,
            @Param("paramName") String paramName,
            @Param("valueType") ValueType valueType,
            @Param("inputValue") String inputValue
    );

    @Transactional
    @Modifying
    @Query(" update RestClientQueryParam a " +
            " set " +
                "a.paramName = :paramName," +
                "a.valueType = :valueType, " +
                " a.inputValue = :inputValue "+
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("paramName") String paramName,
            @Param("valueType") ValueType valueType,
            @Param("inputValue") String inputValue
    );

    List<RestClientQueryParam> findByClientId(Long clientId);
}
