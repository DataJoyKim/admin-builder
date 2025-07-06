package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.restclient.RestClientBody;
import com.datajoy.admin_builder.restclient.code.MessageDataType;
import com.datajoy.admin_builder.restclient.code.ValueType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsoleRestClientBodyRepository extends JpaRepository<RestClientBody, Long> {
    @Transactional
    @Modifying
    @Query(" insert RestClientBody a (clientId,paramName,parentParamName,dataType,valueType,orderNum,inputValue)" +
            " values (:clientId,:paramName,:parentParamName,:dataType,:valueType,:orderNum,:inputValue)")
    void insert(
        @Param("clientId") Long clientId,
        @Param("paramName") String paramName,
        @Param("parentParamName") String parentParamName,
        @Param("dataType") MessageDataType dataType,
        @Param("valueType") ValueType valueType,
        @Param("orderNum") Integer orderNum,
        @Param("inputValue") String inputValue
    );

    @Transactional
    @Modifying
    @Query(" update RestClientBody a " +
            " set " +
                " a.paramName = :paramName, "+
                " a.parentParamName = :parentParamName, "+
                " a.dataType = :dataType, "+
                " a.valueType = :valueType, "+
                " a.orderNum = :orderNum, "+
                " a.inputValue = :inputValue "+
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("paramName") String paramName,
            @Param("parentParamName") String parentParamName,
            @Param("dataType") MessageDataType dataType,
            @Param("valueType") ValueType valueType,
            @Param("orderNum") Integer orderNum,
            @Param("inputValue") String inputValue
    );

    List<RestClientBody> findByClientId(Long clientId);
}
