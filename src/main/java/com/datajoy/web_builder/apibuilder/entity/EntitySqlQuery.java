package com.datajoy.web_builder.apibuilder.entity;

import com.datajoy.web_builder.apibuilder.sql.SqlParameter;
import com.datajoy.web_builder.apibuilder.sql.SqlQuery;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder
public class EntitySqlQuery {
    private String seq;
    private SqlQuery sqlQuery;

    public static EntitySqlQuery createEntitySqlQuery(String seq, String sql, List<SqlParameter> sqlParameters) {
        return EntitySqlQuery.builder()
                .seq(seq)
                .sqlQuery(SqlQuery.builder()
                        .sql(sql)
                        .sqlParameters(sqlParameters)
                        .build())
                .build();
    }
}
