package com.datajoy.admin_builder.apibuilder.sql.parameterbind;

import com.datajoy.admin_builder.apibuilder.sql.SqlQuery;

public class IndexBind implements ParameterBinder {
    @Override
    public SqlQuery binding(SqlQuery sqlQuery) {
        return sqlQuery;
    }
}
