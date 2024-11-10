package com.datajoy.admin_builder.apibuilder.datasource.database;

public class DatabaseMysql implements Database {
    @Override
    public String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public String getValidationQuery() {
        return "select 1";
    }
}
