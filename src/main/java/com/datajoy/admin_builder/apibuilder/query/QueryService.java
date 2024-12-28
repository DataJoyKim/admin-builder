package com.datajoy.admin_builder.apibuilder.query;

import com.datajoy.admin_builder.apibuilder.sql.SqlExecutor;
import com.datajoy.admin_builder.apibuilder.sql.SqlQuery;
import com.datajoy.admin_builder.apibuilder.sql.parameterbind.ParameterBindType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final QueryRepository queryRepository;

    public QueryResult execute(String entityName, QueryRequest params) {
        Query query = queryRepository.findByQueryName(entityName)
                .orElseThrow();

        SqlQuery sqlQuery = query.generateQuery(params);

        SqlExecutor sqlExecutor = SqlExecutor.createSqlExecutor(query.getDataSourceName());
        
        List<Map<String, Object>> resultData = sqlExecutor.execute(sqlQuery, ParameterBindType.NAME_BIND);

        return QueryResult.createQueryResult(resultData);
    }
}
