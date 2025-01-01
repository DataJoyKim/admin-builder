package com.datajoy.admin_builder.apibuilder.datasource;

import com.datajoy.admin_builder.apibuilder.datasource.database.DataSourceDatabaseMeta;
import com.datajoy.admin_builder.apibuilder.datasource.database.DataSourceDatabaseRegister;
import com.datajoy.admin_builder.apibuilder.datasource.restserver.DataSourceRestServer;
import com.datajoy.admin_builder.apibuilder.datasource.restserver.DataSourceRestServerRegister;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DataSourceInitializer {
    @Autowired
    private DataSourceService dataSourceService;

    @PostConstruct
    public void loadDataSource() {
        List<DataSourceDatabaseMeta> databases = dataSourceService.getDatabaseMetadata();
        DataSourceDatabaseRegister.initialize(databases);

        List<DataSourceRestServer> restServers = dataSourceService.getDataSourceRestServer();
        DataSourceRestServerRegister.initialize(restServers);
    }
}
