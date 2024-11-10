package com.datajoy.admin_builder.apibuilder.datasource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceMetaRepository extends JpaRepository<DataSourceDatabaseMeta, Long> {
}
