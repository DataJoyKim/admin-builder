package com.datajoy.web_builder.console;

import com.datajoy.web_builder.apibuilder.datasource.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceRepository extends JpaRepository<DataSource, Long> {
}
