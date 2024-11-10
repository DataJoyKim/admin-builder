package com.datajoy.admin_builder.apibuilder.sql.parameterbind;

import com.datajoy.admin_builder.apibuilder.sql.SqlQuery;

public interface ParameterBinder {
    SqlQuery binding(SqlQuery sqlQuery);
}
