package com.datajoy.admin_builder.sql.parameterbind;

import com.datajoy.admin_builder.sql.SqlQuery;

public interface ParameterBinder {
    SqlQuery binding(SqlQuery sqlQuery);
}
