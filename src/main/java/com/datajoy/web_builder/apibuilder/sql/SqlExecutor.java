package com.datajoy.web_builder.apibuilder.sql;

import com.datajoy.web_builder.apibuilder.sql.parameterbind.ParameterBindType;
import com.datajoy.web_builder.apibuilder.sql.parameterbind.ParameterBinder;
import com.datajoy.web_builder.apibuilder.sql.parameterbind.ParameterBinderFactory;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SqlExecutor {
    private final DataSource dataSource;

    public List<Map<String, Object>> selectList(SqlQuery sqlQuery, ParameterBindType paramBindingType) {
        Connection conn = null;
        PreparedStatement stmt = null;

        ParameterBinder parameterBinder = ParameterBinderFactory.instance(paramBindingType);

        assert parameterBinder != null;

        sqlQuery = parameterBinder.binding(sqlQuery);

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sqlQuery.getSql());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                assert conn != null;
                assert stmt != null;

                conn.close();
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return new ArrayList<>();
    }
}
