package com.datajoy.admin_builder.datasource.database.schema;

import com.datajoy.admin_builder.entity.code.ColumnType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnSchema {
    private String columnName;
    private String dataType;
    private ColumnType columnType;
    private Boolean primaryKey;
    private String comment;
}
