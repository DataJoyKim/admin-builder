package com.datajoy.web_builder.apibuilder.sql;

import com.datajoy.web_builder.apibuilder.datasource.BusinessDataSource;
import com.datajoy.web_builder.apibuilder.datasource.DataSourceMeta;
import com.datajoy.web_builder.apibuilder.sql.parameterbind.ParameterBindType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SqlExecutorTest {

    @Test
    public void executeTest() {
        // Given
        Map<String, Object> params = new HashMap<>();
        params.put("dataSourceName","dataSourceGoal");
        params.put("displayName", "test");
        params.put("note", "test");
        params.put("url", "jdbc:mariadb://localhost:3306/goalset");
        params.put("username", "D1ADMIN");
        params.put("password", "rlaskrdud1!");
        params.put("databaseKind","MARIADB");
        params.put("maximumPoolSize", "10");
        params.put("minimumIdle", "10");
        params.put("connectionTimeout", "60000");
        params.put("validationTimeout", "60000");

        String createSql = """
                CREATE TABLE `test` (
                    `test` VARCHAR(10) NOT NULL COLLATE 'utf8_general_ci'
                )
                COLLATE='utf8_general_ci'
                ENGINE=InnoDB
                AUTO_INCREMENT=14;
                """;

        String insertSql = "insert test(test) values(#{test});";

        List<SqlParameter> insertParams = new ArrayList<>();
        insertParams.add(SqlParameter.builder()
                .parameterName("test")
                .value("init")
                .parameterIndex(1)
                .build());

        String selectSql = "select * from test where test = #{test} order by test;";

        List<SqlParameter> selectParams1 = new ArrayList<>();
        selectParams1.add(SqlParameter.builder()
                .parameterName("test")
                .value("init")
                .parameterIndex(1)
                .build());

        List<SqlParameter> selectParams2 = new ArrayList<>();
        selectParams2.add(SqlParameter.builder()
                .parameterName("test")
                .value("success")
                .parameterIndex(1)
                .build());

        String updateSql = "update test set test = #{test} where test = #{whereTest};";

        List<SqlParameter> updateParams = new ArrayList<>();
        updateParams.add(SqlParameter.builder()
                .parameterName("test")
                .value("success")
                .parameterIndex(1)
                .build());
        updateParams.add(SqlParameter.builder()
                .parameterName("whereTest")
                .value("init")
                .parameterIndex(2)
                .build());

        String deleteSql = "delete from test where test = #{test};";

        List<SqlParameter> deleteParams = new ArrayList<>();
        deleteParams.add(SqlParameter.builder()
                .parameterName("test")
                .value("success")
                .parameterIndex(1)
                .build());

        String dropSql = "DROP TABLE `test`;";

        // When
        DataSourceMeta meta = DataSourceMeta.createDataSource(params);

        DataSource dataSource = BusinessDataSource.toDataSource(meta);

        SqlExecutor executor = new SqlExecutor(dataSource);

        executor.execute(SqlQuery.builder()
                .sql(createSql)
                .build(), ParameterBindType.NAME_BIND);

        executor.execute(SqlQuery.builder()
                .sql(insertSql)
                .sqlParameters(insertParams)
                .build(), ParameterBindType.NAME_BIND);

        List<Map<String,Object>> selectResults = executor.execute(SqlQuery.builder()
                .sql(selectSql)
                .sqlParameters(selectParams1)
                .build(), ParameterBindType.NAME_BIND);

        executor.execute(SqlQuery.builder()
                .sql(updateSql)
                .sqlParameters(updateParams)
                .build(), ParameterBindType.NAME_BIND);

        List<Map<String,Object>> selectResults2 = executor.execute(SqlQuery.builder()
                .sql(selectSql)
                .sqlParameters(selectParams2)
                .build(), ParameterBindType.NAME_BIND);

        executor.execute(SqlQuery.builder()
                .sql(deleteSql)
                .sqlParameters(deleteParams)
                .build(), ParameterBindType.NAME_BIND);

        List<Map<String,Object>> selectResults3 = executor.execute(SqlQuery.builder()
                .sql(selectSql)
                .sqlParameters(selectParams2)
                .build(), ParameterBindType.NAME_BIND);

        executor.execute(SqlQuery.builder()
                .sql(dropSql)
                .build(), ParameterBindType.NAME_BIND);

        // Then
        Assertions.assertEquals(1, selectResults.size());
        Assertions.assertEquals(1, selectResults2.size());
        Assertions.assertEquals(0, selectResults3.size());
    }
}