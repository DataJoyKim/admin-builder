package com.datajoy.admin_builder.apibuilder.datasource.database;

import com.datajoy.admin_builder.apibuilder.datasource.ConnectValidation;
import com.datajoy.admin_builder.apibuilder.datasource.LookupKey;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DataSourceDatabaseRegister {
    private static Map<LookupKey, DataSource> dataSourceMap;

    public static void initialize(List<DataSourceDatabaseMeta> metadataList) {
        dataSourceMap = new HashMap<>();

        for(DataSourceDatabaseMeta meta : metadataList) {
            try {
                dataSourceMap.put(LookupKey.generateKey(meta), meta.createDataSource());
                log.info("BusinessDataSource - initialized businessDataSource : [{}]", meta.getDataSourceName());
            }
            catch (Exception e) {
                log.error("BusinessDataSource - initialize failed.. businessDataSource: [{}]", meta.getDataSourceName());
                log.error("error", e);
            }
        }
    }

    public static Map<LookupKey, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public static DataSource getDataSource(LookupKey lookupKey) {
        return dataSourceMap.get(lookupKey);
    }

    public static void registry(LookupKey lookupKey, DataSource dataSource) {
        dataSourceMap.put(lookupKey, dataSource);
    }

    public static void registry(DataSourceDatabaseMeta meta) {
        DataSource dataSource = meta.createDataSource();

        LookupKey lookupKey = LookupKey.generateKey(meta);

        registry(lookupKey, dataSource);
    }
    public static ConnectValidation validateConnect(DataSourceDatabaseMeta metadata) {
        Connection conn = null;
        Statement stmt = null;

        LookupKey lookupKey = LookupKey.generateKey(metadata.getDataSourceName());

        DataSource dataSource = dataSourceMap.get(lookupKey);

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