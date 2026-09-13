package com.prometis.appbuilder.executor.sql.parameterbind;

import com.prometis.appbuilder.executor.sql.SqlQuery;

public class IndexBind implements ParameterBinder {
    @Override
    public SqlQuery binding(SqlQuery sqlQuery) {
        return sqlQuery;
    }
}
