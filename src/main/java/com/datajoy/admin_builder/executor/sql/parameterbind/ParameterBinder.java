package com.datajoy.admin_builder.executor.sql.parameterbind;

import com.datajoy.admin_builder.executor.sql.SqlQuery;

public interface ParameterBinder {
    SqlQuery binding(SqlQuery sqlQuery);
}
