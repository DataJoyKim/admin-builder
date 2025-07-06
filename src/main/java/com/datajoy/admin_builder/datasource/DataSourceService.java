package com.datajoy.admin_builder.datasource;

import com.datajoy.admin_builder.datasource.database.DataSourceDatabaseMeta;
import com.datajoy.admin_builder.datasource.database.DataSourceDatabaseMetaRepository;
import com.datajoy.admin_builder.datasource.restserver.DataSourceRestServer;
import com.datajoy.admin_builder.datasource.restserver.DataSourceRestServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSourceService {
    private final DataSourceDatabaseMetaRepository dataSourceDatabaseMetaRepository;
    private final DataSourceRestServerRepository dataSourceRestServerRepository;

    public List<DataSourceDatabaseMeta> getDatabaseMetadata() {
        return dataSourceDatabaseMetaRepository.findAll();
    }

    public List<DataSourceRestServer> getDataSourceRestServer() {
        return dataSourceRestServerRepository.findAll();
    }
}
