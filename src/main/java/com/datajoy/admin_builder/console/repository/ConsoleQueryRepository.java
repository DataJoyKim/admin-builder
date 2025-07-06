package com.datajoy.admin_builder.console.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsoleQueryRepository extends JpaRepository<com.datajoy.admin_builder.query.Query, Long> {

    @Transactional
    @Modifying
    @Query(" update Query a " +
            " set " +
                "a.queryName = :queryName,"+
                "a.displayName = :displayName,"+
                "a.dataSourceName = :dataSourceName,"+
                "a.query = :query"+
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("queryName") String queryName,
            @Param("displayName") String displayName,
            @Param("dataSourceName") String dataSourceName,
            @Param("query") String query
    );
}
