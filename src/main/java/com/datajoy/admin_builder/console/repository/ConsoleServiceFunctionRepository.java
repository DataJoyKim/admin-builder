package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.apibuilder.function.code.FunctionType;
import com.datajoy.admin_builder.apibuilder.function.ServiceFunction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsoleServiceFunctionRepository extends JpaRepository<ServiceFunction, Long> {

    @Transactional
    @Modifying
    @Query(" insert ServiceFunction a (" +
                "serviceId," +
                "functionName," +
                "functionType," +
                "orderNum," +
                "isLogging," +
                "requestMessageId," +
                "responseMessageId" +
            ")" +
            " values ( " +
                ":serviceId," +
                ":functionName," +
                ":functionType," +
                ":orderNum," +
                ":isLogging," +
                ":requestMessageId," +
                ":responseMessageId" +
            ")")
    void insert(
        @Param("serviceId") Long serviceId,
        @Param("functionName") String functionName,
        @Param("functionType") FunctionType functionType,
        @Param("orderNum") Integer orderNum,
        @Param("isLogging") Boolean isLogging,
        @Param("requestMessageId") String requestMessageId,
        @Param("responseMessageId") String responseMessageId
    );

    @Transactional
    @Modifying
    @Query(" update ServiceFunction a " +
            " set " +
                "a.serviceId = :serviceId," +
                "a.functionName = :functionName," +
                "a.functionType = :functionType," +
                "a.orderNum = :orderNum," +
                "a.isLogging = :isLogging," +
                "a.requestMessageId = :requestMessageId," +
                "a.responseMessageId = :responseMessageId" +
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("serviceId") Long serviceId,
            @Param("functionName") String functionName,
            @Param("functionType") FunctionType functionType,
            @Param("orderNum") Integer orderNum,
            @Param("isLogging") Boolean isLogging,
            @Param("requestMessageId") String requestMessageId,
            @Param("responseMessageId") String responseMessageId
    );

    List<ServiceFunction> findByServiceIdOrderByOrderNum(Long serviceId);
}
