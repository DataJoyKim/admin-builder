package com.datajoy.admin_builder.sql;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SqlExecutorPrint {
    public static void print(SqlQuery sqlQuery) {

        StringBuilder printQuery = new StringBuilder();

        printQuery.append(System.lineSeparator());
        printQuery.append("==================== SQL ====================")
                .append(System.lineSeparator());
        printQuery.append(sqlQuery.getSql())
                .append(System.lineSeparator());
        printQuery.append("---------------------------------------------")
                .append(System.lineSeparator());

        for (SqlParameter sqlParameter : sqlQuery.getSqlParameters()) {
            printQuery.append(sqlParameter.getParameterName())
                    .append(" | ")
                    .append(sqlParameter.getParameterIndex())
                    .append(" | ")
                    .append(sqlParameter.getValue())
                    .append(System.lineSeparator());
        }

        printQuery.append("=============================================");

        log.info(printQuery.toString());
    }
}
