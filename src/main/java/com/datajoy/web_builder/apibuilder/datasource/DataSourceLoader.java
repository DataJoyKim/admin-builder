package com.datajoy.web_builder.apibuilder.datasource;

import com.datajoy.web_builder.apibuilder.datasource.DataSourceMeta;
import com.datajoy.web_builder.apibuilder.datasource.DataSourceService;
import com.datajoy.web_builder.apibuilder.datasource.database.Database;
import com.datajoy.web_builder.apibuilder.datasource.database.DatabaseFactory;
import com.zaxxer.hikari.HikariDataSource;
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

        List<DataSourceMeta> dataSourceMetaList = dataSourceService.getMetadata();

        for(DataSourceMeta meta : dataSourceMetaList) {
            log.info("DataSourceLoader - dataSource bean name: [{}]", meta.getDataSourceName());

            try {
                Database database = DatabaseFactory.instance(meta.getDatabaseKind());

                HikariDataSource dataSource = new HikariDataSource();
                dataSource.setDriverClassName(database.getDriverClassName());
                dataSource.setJdbcUrl(meta.getUrl());
                dataSource.setUsername(meta.getUsername());
                dataSource.setPassword(meta.getPassword());
                dataSource.setMaximumPoolSize(meta.getMaximumPoolSize());
                dataSource.setMinimumIdle(meta.getMinimumIdle());
                dataSource.setConnectionTimeout(meta.getConnectionTimeout());
                dataSource.setValidationTimeout(meta.getValidationTimeout());
                dataSource.setConnectionTestQuery(database.getValidationQuery());

                beanFactory.registerSingleton(meta.getDataSourceName(), dataSource);
            }
            catch (Exception e) {
                log.error("DataSourceLoader - load failed.. dataSource: [{}]", meta.getDataSourceName());
                log.error("error", e);
            }
        }
    }
}
