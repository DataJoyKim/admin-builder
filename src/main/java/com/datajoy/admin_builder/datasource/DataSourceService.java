package com.datajoy.admin_builder.datasource;

import com.datajoy.admin_builder.datasource.database.DataSourceDatabaseMeta;
import com.datajoy.admin_builder.datasource.database.DataSourceDatabaseMetaRepository;
import com.datajoy.admin_builder.datasource.notification.NotificationProvider;
import com.datajoy.admin_builder.datasource.notification.NotificationProviderRepository;
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
    private final NotificationProviderRepository notificationProviderRepository;

    public List<DataSourceDatabaseMeta> getDatabaseMetadata() {
        return dataSourceDatabaseMetaRepository.findAll();
    }

    public List<DataSourceRestServer> getDataSourceRestServer() {
        return dataSourceRestServerRepository.findAll();
    }

    public List<NotificationProvider> getNotificationProvider() {
        return notificationProviderRepository.findAll();
    }
}
