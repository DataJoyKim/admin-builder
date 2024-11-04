package com.datajoy.web_builder.console.repository;

import com.datajoy.web_builder.apibuilder.entity.EntityColumn;
import com.datajoy.web_builder.apibuilder.entity.code.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsoleEntityColumnRepository extends JpaRepository<EntityColumn, Long> {

    @Transactional
    @Modifying
    @Query(" insert EntityColumn a (entityId,columnName,displayName,useKey,columnType)" +
            " values ( " +
            ":entityId," +
            ":columnName," +
            ":displayName," +
            ":useKey," +
            ":columnType" +
            ")")
    void insert(
            @Param("entityId") Long entityId,
            @Param("columnName") String columnName,
            @Param("displayName") String displayName,
            @Param("useKey") Boolean useKey,
            @Param("columnType") ColumnType columnType
    );

    @Transactional
    @Modifying
    @Query(" update EntityColumn a " +
            " set " +
                "a.entityId = :entityId," +
                "a.columnName = :columnName," +
                "a.displayName = :displayName," +
                "a.useKey = :useKey," +
                "a.columnType = :columnType," +
                "a.selectWhereType = :selectWhereType," +
                "a.selectWhereCompareOperator = :selectWhereCompareOperator," +
                "a.selectOrderByNum = :selectOrderByNum," +
                "a.selectOrderBySortOrder = :selectOrderBySortOrder," +
                "a.insertAutoValueType = :insertAutoValueType," +
                "a.insertAutoValue = :insertAutoValue," +
                "a.insertNullResolveType = :insertNullResolveType," +
                "a.updateAutoValueType = :updateAutoValueType," +
                "a.updateAutoValue = :updateAutoValue," +
                "a.updateNullResolveType = :updateNullResolveType," +
                "a.deleteAutoValueType = :deleteAutoValueType," +
                "a.deleteAutoValue = :deleteAutoValue" +
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("entityId") Long entityId,
            @Param("columnName") String columnName,
            @Param("displayName") String displayName,
            @Param("useKey") Boolean useKey,
            @Param("columnType") ColumnType columnType,
            @Param("selectWhereType") SelectWhereType selectWhereType,
            @Param("selectWhereCompareOperator") String selectWhereCompareOperator,
            @Param("selectOrderByNum") Integer selectOrderByNum,
            @Param("selectOrderBySortOrder") SortOrder selectOrderBySortOrder,
            @Param("insertAutoValueType") AutoValueType insertAutoValueType,
            @Param("insertAutoValue") String insertAutoValue,
            @Param("insertNullResolveType") NullResolveType insertNullResolveType,
            @Param("updateAutoValueType") AutoValueType updateAutoValueType,
            @Param("updateAutoValue") String updateAutoValue,
            @Param("updateNullResolveType") NullResolveType updateNullResolveType,
            @Param("deleteAutoValueType") AutoValueType deleteAutoValueType,
            @Param("deleteAutoValue") String deleteAutoValue
    );

    List<EntityColumn> findByEntityId(Long entityId);
}
