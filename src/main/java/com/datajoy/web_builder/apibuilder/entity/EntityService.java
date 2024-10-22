package com.datajoy.web_builder.apibuilder.entity;

import com.datajoy.web_builder.apibuilder.datasource.BusinessDataSource;
import com.datajoy.web_builder.apibuilder.datasource.LookupKey;
import com.datajoy.web_builder.apibuilder.entity.repository.EntityRepository;
import com.datajoy.web_builder.apibuilder.sql.SqlExecutor;
import com.datajoy.web_builder.apibuilder.sql.SqlQuery;
import com.datajoy.web_builder.apibuilder.sql.parameterbind.ParameterBindType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EntityService {
    private final EntityRepository entityRepository;
    private final EntityConfig entityConfig;
    private final BusinessDataSource businessDataSource;

    public EntityResult execute(String entityName, EntityParameter params) {
        Entity entity = entityRepository.findByEntityName(entityName)
                                        .orElseThrow();

        List<EntitySqlQuery> entitySqlQueryList = entity.generateQuery(entityConfig, params);

        SqlExecutor sqlExecutor = createSqlExecutor(entity);

        Map<String, List<Map<String, Object>>> results = new HashMap<>();

        for(EntitySqlQuery entitySqlQuery : entitySqlQueryList) {
            List<Map<String, Object>> resultData = sqlExecutor.execute(entitySqlQuery.getSqlQuery(), ParameterBindType.NAME_BIND);

            results.put(entitySqlQuery.getSeq(), resultData);
        }

        return EntityResult.createEntityResult(results);
    }

    private SqlExecutor createSqlExecutor(Entity entity) {
        DataSource dataSource = businessDataSource.getDataSource(LookupKey.generateKey(entity.getDataSourceName()));

        return new SqlExecutor(dataSource);
    }
}
