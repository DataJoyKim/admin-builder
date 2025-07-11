package com.datajoy.admin_builder.query;

import com.datajoy.admin_builder.query.code.QueryResultCode;
import com.datajoy.admin_builder.sql.SqlExecutor;
import com.datajoy.admin_builder.sql.SqlQuery;
import com.datajoy.admin_builder.sql.parameterbind.ParameterBindType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryService {
    private final QueryRepository queryRepository;

    public QueryResult execute(String queryName, QueryRequest params) {
        Query query = queryRepository.findByQueryName(queryName)
                .orElseThrow();

        SqlQuery sqlQuery = query.generateQuery(params);

        SqlExecutor sqlExecutor = SqlExecutor.createSqlExecutor(query.getDataSourceName());

        List<Map<String, Object>> resultData = new ArrayList<>();

        try {
            resultData = sqlExecutor.execute(sqlQuery, ParameterBindType.NAME_BIND);

            return QueryResult.createQueryResult(QueryResultCode.SUCCESS, resultData);
        }
        catch (SQLException e) {
            log.error("error",e);
            Map<String, Object> error = new HashMap<>();
            error.put("errorCode", e.getErrorCode());
            error.put("message",e.getMessage());
            resultData.add(error);

            return QueryResult.createQueryResult(QueryResultCode.FAILURE, resultData);
        }
    }
}
