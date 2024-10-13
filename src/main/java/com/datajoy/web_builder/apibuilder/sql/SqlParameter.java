package com.datajoy.web_builder.apibuilder.sql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SqlParameter {
    private String parameterName;
    private Integer parameterIndex;

    public static SqlParameter createSqlParameter(
            String parameterName,
            Integer parameterIndex
    ) {
        return SqlParameter.builder()
                .parameterName(parameterName)
                .parameterIndex(parameterIndex)
                .build();
    }
}
