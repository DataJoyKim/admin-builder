package com.datajoy.admin_builder.apibuilder.datasource;

import com.datajoy.admin_builder.apibuilder.datasource.database.Database;
import com.datajoy.admin_builder.apibuilder.datasource.database.DatabaseFactory;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class DataSourceDatabase {
    private Map<LookupKey, DataSource> dataSourceMap;

    public static DataSourceDatabase initialize(List<DataSourceDatabaseMeta> metadataList) {
        DataSourceDatabase databaseDataSource = new DataSourceDatabase();
        databaseDataSource.dataSourceMap = new HashMap<>();

        for(DataSourceDatabaseMeta meta : metadataList) {
            try {
                databaseDataSource.dataSourceMap.put(LookupKey.generateKey(meta), toDataSource(meta));
                log.info("BusinessDataSource - initialized businessDataSource : [{}]", meta.getDataSourceName());
            }
            catch (Exception e) {
                log.error("BusinessDataSource - initialize failed.. businessDataSource: [{}]", meta.getDataSourceName());
                log.error("error", e);
            }
        }

        return databaseDataSource;
    }

    public DataSource getDataSource(LookupKey lookupKey) {
        return dataSourceMap.get(lookupKey);
    }

    public void registry(LookupKey lookupKey, DataSource dataSource) {
        dataSourceMap.put(lookupKey, dataSource);
    }

    public void registry(DataSourceDatabaseMeta meta) {
        DataSource dataSource = toDataSource(meta);
        LookupKey lookupKey = LookupKey.generateKey(meta);

        registry(lookupKey, dataSource);
    }

    public static DataSource toDataSource(DataSourceDatabaseMeta meta) {
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

        return dataSource;
    }
    public ConnectValidation validateConnect(DataSourceDatabaseMeta metadata) {
        Connection conn = null;
        Statement stmt = null;

        LookupKey lookupKey = LookupKey.generateKey(metadata.getDataSourceName());

        DataSource dataSource = this.dataSourceMap.get(lookupKey);

        ConnectValidation validate = new ConnectValidation();
        try {
            Database database = DatabaseFactory.instance(metadata.getDatabaseKind());

            conn = dataSource.getConnection();
            String sql = database.getValidationQuery();
            stmt = conn.createStatement();
            stmt.executeQuery(sql);

            validate.setResult(true);
        }
        catch (Exception e) {
            validate.setResult(false);
            validate.setErrorStack(e);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return validate;
    }
}
