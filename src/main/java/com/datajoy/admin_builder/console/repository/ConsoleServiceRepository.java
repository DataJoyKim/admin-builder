package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.apibuilder.service.ServiceBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsoleServiceRepository extends JpaRepository<ServiceBuilder, Long> {

    @Transactional
    @Modifying
    @Query(" update ServiceBuilder a " +
            " set " +
                "a.serviceName = :serviceName,"+
                "a.displayName = :displayName,"+
                "a.note = :note"+
            " where a.id = :id")
    void update(
            @Param("id") Long id,
            @Param("serviceName") String serviceName,
            @Param("displayName") String displayName,
            @Param("note") String note
    );
}
