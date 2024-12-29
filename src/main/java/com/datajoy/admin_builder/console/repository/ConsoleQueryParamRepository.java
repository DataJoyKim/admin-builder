package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.apibuilder.query.QueryParam;
import com.datajoy.admin_builder.apibuilder.query.code.AutoValueType;
import com.datajoy.admin_builder.apibuilder.query.code.InOut;
import com.datajoy.admin_builder.apibuilder.query.code.ParamType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsoleQueryParamRepository extends JpaRepository<QueryParam, Long> {
    @Transactional
    @Modifying
    @Query(" insert QueryParam a (queryId,paramName,paramType,autoValueType,inOut)" +
            " values ( " +
                ":queryId," +
                ":paramName," +
                ":paramType," +
                ":autoValueType," +
                ":inOut" +
            ")")
    void insert(
            @Param("queryId") Long queryId,
            @Param("paramName") String paramName,
            @Param("paramType") ParamType paramType,
            @Param("autoValueType") AutoValueType autoValueType,
            @Param("inOut") InOut inOut
    );

    @Transactional
    @Modifying
    @Query(" update QueryParam a " +
            " set " +
                "a.paramName = :paramName," +
                "a.paramType = :paramType," +
                "a.autoValueType = :autoValueType," +
                "a.inOut = :inOut" +
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("paramName") String paramName,
            @Param("paramType") ParamType paramType,
            @Param("autoValueType") AutoValueType autoValueType,
            @Param("inOut") InOut inOut
    );

    List<QueryParam> findByQueryId(Long queryId);
}
