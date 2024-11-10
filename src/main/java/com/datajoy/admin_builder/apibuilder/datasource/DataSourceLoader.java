package com.datajoy.admin_builder.apibuilder.datasource;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DataSourceLoader {
    @Autowired
    private ConfigurableBeanFactory configurableBeanFactory;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DataSourceService dataSourceService;

    @PostConstruct
    public void loadDataSource() {
        var appContent = (ConfigurableApplicationContext) applicationContext;

        ConfigurableBeanFactory beanFactory = appContent.getBeanFactory();

        List<DataSourceDatabaseMeta> metadataList = dataSourceService.getDatabaseMetadata();

        DataSourceDatabase dataSourceDatabase = DataSourceDatabase.initialize(metadataList);

        beanFactory.registerSingleton("dataSourceDatabase", dataSourceDatabase);
    }
}
