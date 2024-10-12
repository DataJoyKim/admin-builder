package com.datajoy.web_builder.apibuilder.datasource.database;

public class DatabaseFactory {

    public static Database instance(DatabaseKind databaseKind) {
        if(databaseKind == null) {
            return null;
        }

        if(DatabaseKind.MYSQL.equals(databaseKind)) {
            return new DatabaseMysql();
        }
        else if(DatabaseKind.MSSQL.equals(databaseKind)) {
            return new DatabaseMssql();
        }
        else if(DatabaseKind.ORACLE.equals(databaseKind)) {
            return new DatabaseOracle();
        }
        else {
            return null;
        }
    }
}
