package com.datajoy.web_builder.apibuilder.sql.parameterbind;

import com.datajoy.web_builder.apibuilder.sql.SqlQuery;

public interface ParameterBinder {
    SqlQuery binding(SqlQuery sqlQuery);
}
