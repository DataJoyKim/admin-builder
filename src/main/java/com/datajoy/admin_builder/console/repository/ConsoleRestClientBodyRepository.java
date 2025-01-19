package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.apibuilder.restclient.RestClientBody;
import com.datajoy.admin_builder.apibuilder.restclient.code.AutoValueType;
import com.datajoy.admin_builder.apibuilder.restclient.code.MessageDataType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsoleRestClientBodyRepository extends JpaRepository<RestClientBody, Long> {
    @Transactional
    @Modifying
    @Query(" insert RestClientBody a (clientId,paramName,parentParamName,dataType,autoValueType,orderNum)" +
            " values (:clientId,:paramName,:parentParamName,:dataType,:autoValueType,:orderNum)")
    void insert(
        @Param("clientId") Long clientId,
        @Param("paramName") String paramName,
        @Param("parentParamName") String parentParamName,
        @Param("dataType") MessageDataType dataType,
        @Param("autoValueType") AutoValueType autoValueType,
        @Param("orderNum") Integer orderNum
    );

    @Transactional
    @Modifying
    @Query(" update RestClientBody a " +
            " set " +
                " a.paramName = :paramName, "+
                " a.parentParamName = :parentParamName, "+
                " a.dataType = :dataType, "+
                " a.autoValueType = :autoValueType, "+
                " a.orderNum = :orderNum "+
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("paramName") String paramName,
            @Param("parentParamName") String parentParamName,
            @Param("dataType") MessageDataType dataType,
            @Param("autoValueType") AutoValueType autoValueType,
            @Param("orderNum") Integer orderNum
    );

    List<RestClientBody> findByClientId(Long clientId);
}
