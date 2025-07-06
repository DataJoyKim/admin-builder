package com.datajoy.admin_builder.datasource.database;

public class DatabaseMariaDB implements Database {
    @Override
    public String getDriverClassName() {
        return "org.mariadb.jdbc.Driver";
    }

    @Override
    public String getValidationQuery() {
        return "select 1";
    }
}
