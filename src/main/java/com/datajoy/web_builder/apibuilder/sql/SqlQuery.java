package com.datajoy.web_builder.apibuilder.sql;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class SqlQuery {
    private String sql;
    private List<SqlParameter> sqlParameters = new ArrayList<>();

    public static SqlQuery createSqlQuery(String sql, List<SqlParameter> sqlParameters) {
        SqlQuery sqlQuery = SqlQuery.builder()
                                .sql(sql)
                                .sqlParameters(sqlParameters)
                                .build();
        return sqlQuery;
    }
}
