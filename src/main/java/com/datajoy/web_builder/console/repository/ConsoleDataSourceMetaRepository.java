package com.datajoy.web_builder.console.repository;

import com.datajoy.web_builder.apibuilder.datasource.DataSourceMeta;
import com.datajoy.web_builder.apibuilder.datasource.database.DatabaseKind;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsoleDataSourceMetaRepository extends JpaRepository<DataSourceMeta, Long> {

    @Transactional
    @Modifying
    @Query(" update DataSourceMeta a " +
            " set " +
                "a.dataSourceName = :dataSourceName,"+
                "a.displayName = :displayName,"+
                "a.note = :note,"+
                "a.url = :url,"+
                "a.username = :username,"+
                "a.password = :password,"+
                "a.databaseKind = :databaseKind,"+
                "a.maximumPoolSize = :maximumPoolSize,"+
                "a.minimumIdle = :minimumIdle,"+
                "a.connectionTimeout = :connectionTimeout,"+
                "a.validationTimeout = :validationTimeout"+
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("dataSourceName") String dataSourceName,
            @Param("displayName") String displayName,
            @Param("note") String note,
            @Param("url") String url,
            @Param("username") String username,
            @Param("password") String password,
            @Param("databaseKind") DatabaseKind databaseKind,
            @Param("maximumPoolSize") Integer maximumPoolSize,
            @Param("minimumIdle") Integer minimumIdle,
            @Param("connectionTimeout") Integer connectionTimeout,
            @Param("validationTimeout") Integer validationTimeout
    );
}
