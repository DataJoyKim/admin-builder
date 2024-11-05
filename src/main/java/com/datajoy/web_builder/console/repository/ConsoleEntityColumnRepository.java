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
                "a.columnName = :columnName," +
                "a.displayName = :displayName," +
                "a.useKey = :useKey," +
                "a.columnType = :columnType," +
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("columnName") String columnName,
            @Param("displayName") String displayName,
            @Param("useKey") Boolean useKey,
            @Param("columnType") ColumnType columnType
    );

    @Transactional
    @Modifying
    @Query(" update EntityColumn a " +
            " set " +
            "a.selectWhereType = :selectWhereType," +
            "a.selectWhereCompareOperator = :selectWhereCompareOperator," +
            "a.selectOrderByNum = :selectOrderByNum," +
            "a.selectOrderBySortOrder = :selectOrderBySortOrder," +
            " where a.id = :id")
    void updateSelectSetting(
            @Param("id") Long id,
            @Param("selectWhereType") SelectWhereType selectWhereType,
            @Param("selectWhereCompareOperator") String selectWhereCompareOperator,
            @Param("selectOrderByNum") Integer selectOrderByNum,
            @Param("selectOrderBySortOrder") SortOrder selectOrderBySortOrder
    );

    @Transactional
    @Modifying
    @Query(" update EntityColumn a " +
            " set " +
            "a.insertAutoValueType = :insertAutoValueType," +
            "a.insertAutoValue = :insertAutoValue," +
            "a.insertNullResolveType = :insertNullResolveType," +
            " where a.id = :id")
    void updateInsertSetting(
            @Param("id") Long id,
            @Param("insertAutoValueType") AutoValueType insertAutoValueType,
            @Param("insertAutoValue") String insertAutoValue,
            @Param("insertNullResolveType") NullResolveType insertNullResolveType
    );

    @Transactional
    @Modifying
    @Query(" update EntityColumn a " +
            " set " +
            "a.updateAutoValueType = :updateAutoValueType," +
            "a.updateAutoValue = :updateAutoValue," +
            "a.updateNullResolveType = :updateNullResolveType," +
            " where a.id = :id")
    void updateUpdateSetting(
            @Param("id") Long id,
            @Param("updateAutoValueType") AutoValueType updateAutoValueType,
            @Param("updateAutoValue") String updateAutoValue,
            @Param("updateNullResolveType") NullResolveType updateNullResolveType
    );

    @Transactional
    @Modifying
    @Query(" update EntityColumn a " +
            " set " +
            "a.deleteAutoValueType = :deleteAutoValueType," +
            "a.deleteAutoValue = :deleteAutoValue" +
            " where a.id = :id")
    void updateDeleteSetting(
            @Param("id") Long id,
            @Param("deleteAutoValueType") AutoValueType deleteAutoValueType,
            @Param("deleteAutoValue") String deleteAutoValue
    );

    List<EntityColumn> findByEntityId(Long entityId);
}
