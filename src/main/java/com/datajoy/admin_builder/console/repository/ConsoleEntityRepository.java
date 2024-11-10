package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.apibuilder.entity.Entity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsoleEntityRepository extends JpaRepository<Entity, Long> {

    @Transactional
    @Modifying
    @Query(" update Entity a " +
            " set " +
            "a.entityName = :entityName,"+
            "a.displayName = :displayName,"+
            "a.dataSourceName = :dataSourceName,"+
            "a.tableName = :tableName"+
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("entityName") String entityName,
            @Param("displayName") String displayName,
            @Param("dataSourceName") String dataSourceName,
            @Param("tableName") String tableName
    );
}
