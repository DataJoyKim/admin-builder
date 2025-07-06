package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.restclient.RestClientHeader;
import com.datajoy.admin_builder.restclient.code.ValueType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsoleRestClientHeaderRepository extends JpaRepository<RestClientHeader, Long> {
    @Transactional
    @Modifying
    @Query(" insert RestClientHeader a (clientId,name,valueType,inputValue)" +
            " values ( " +
                ":clientId," +
                ":name," +
                ":valueType," +
                ":inputValue" +
            ")")
    void insert(
            @Param("clientId") Long clientId,
            @Param("name") String name,
            @Param("valueType") ValueType valueType,
            @Param("inputValue") String inputValue
    );

    @Transactional
    @Modifying
    @Query(" update RestClientHeader a " +
            " set " +
                "a.name = :name," +
                "a.valueType = :valueType," +
                "a.inputValue = :inputValue" +
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("valueType") ValueType valueType,
            @Param("inputValue") String inputValue
    );

    List<RestClientHeader> findByClientId(Long clientId);
}
