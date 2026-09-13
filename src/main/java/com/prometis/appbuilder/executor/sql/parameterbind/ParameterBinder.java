package com.prometis.appbuilder.executor.sql.parameterbind;

import com.prometis.appbuilder.executor.sql.SqlQuery;

public interface ParameterBinder {
    SqlQuery binding(SqlQuery sqlQuery);
}
