package com.datajoy.admin_builder.datasource.database;

public class DatabaseOracle implements Database {
    @Override
    public String getDriverClassName() {
        return "oracle.jdbc.driver.OracleDriver";
    }

    @Override
    public String getValidationQuery() {
        return "select 1 from dual";
    }
}
