package com.datajoy.admin_builder.apibuilder.datasource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSourceService {
    private final DataSourceMetaRepository dataSourceMetaRepository;

    public List<DataSourceDatabaseMeta> getDatabaseMetadata() {
        return dataSourceMetaRepository.findAll();
    }
}
