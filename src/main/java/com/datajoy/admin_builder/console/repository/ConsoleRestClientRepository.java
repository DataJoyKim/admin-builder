package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.restclient.RestClient;
import com.datajoy.admin_builder.restclient.code.BodyMessageFormat;
import com.datajoy.admin_builder.restclient.code.ContentType;
import com.datajoy.admin_builder.restclient.code.HttpMethod;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsoleRestClientRepository extends JpaRepository<RestClient, Long> {

    @Transactional
    @Modifying
    @Query(" update RestClient a " +
            " set " +
                "a.clientName = :clientName,"+
                "a.displayName = :displayName,"+
                "a.dataSourceName = :dataSourceName,"+
                "a.method = :method,"+
                "a.path = :path,"+
                "a.contentType = :contentType,"+
                "a.bodyMessageFormat = :bodyMessageFormat"+
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("clientName") String clientName,
            @Param("displayName") String displayName,
            @Param("dataSourceName") String dataSourceName,
            @Param("method") HttpMethod method,
            @Param("path") String path,
            @Param("contentType") ContentType contentType,
            @Param("bodyMessageFormat") BodyMessageFormat bodyMessageFormat
    );

    List<RestClient> findByClientName(String clientName);
}
