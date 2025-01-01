package com.datajoy.admin_builder.apibuilder.sql;

import com.datajoy.admin_builder.apibuilder.datasource.database.DataSourceDatabaseRegister;
import com.datajoy.admin_builder.apibuilder.datasource.LookupKey;
import com.datajoy.admin_builder.apibuilder.sql.parameterbind.ParameterBindType;
import com.datajoy.admin_builder.apibuilder.sql.parameterbind.ParameterBinder;
import com.datajoy.admin_builder.apibuilder.sql.parameterbind.ParameterBinderFactory;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SqlExecutor {
    private final DataSource dataSource;

    public List<Map<String, Object>> execute(SqlQuery sqlQuery, ParameterBindType paramBindingType) throws SQLException {
        List<Map<String, Object>> resultList;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ParameterBinder parameterBinder = ParameterBinderFactory.instance(paramBindingType);

        assert parameterBinder != null;

        sqlQuery = parameterBinder.binding(sqlQuery);

        try {
            conn = dataSource.getConnection();

            stmt = conn.prepareStatement(sqlQuery.getSql());

            for(SqlParameter sqlParameter : sqlQuery.getSqlParameters()) {
                stmt.setObject(sqlParameter.getParameterIndex(), sqlParameter.getValue());
            }

            rs = stmt.executeQuery();

            resultList = mapping(rs);
        }
        finally {
            if(rs != null) {
                rs.close();
            }

            if(stmt != null) {
                stmt.close();
            }

            if(conn != null) {
                conn.close();
            }
        }

        return resultList;
    }

    private List<Map<String, Object>> mapping(ResultSet rs) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        ResultSetMetaData rsMeta = rs.getMetaData();
        int columnCount = rsMeta.getColumnCount();

        while(rs.next()) {
            Map<String, Object> rows = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                rows.put(rsMeta.getColumnName(i), rs.getObject(i));
            }

            resultList.add(rows);
        }

        return resultList;
    }

    public static SqlExecutor createSqlExecutor(String dataSourceName) {
        LookupKey lookupKey = LookupKey.generateKey(dataSourceName);

        DataSource dataSource = DataSourceDatabaseRegister.getDataSource(lookupKey);

        return new SqlExecutor(dataSource);
    }
}
