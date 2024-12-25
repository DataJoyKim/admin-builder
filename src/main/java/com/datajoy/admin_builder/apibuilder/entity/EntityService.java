package com.datajoy.admin_builder.apibuilder.entity;

import com.datajoy.admin_builder.apibuilder.sql.SqlExecutor;
import com.datajoy.admin_builder.apibuilder.sql.parameterbind.ParameterBindType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EntityService {
    private final EntityRepository entityRepository;
    private final EntityConfig entityConfig;

    public EntityResult execute(String entityName, EntityRequest params) {
        Entity entity = entityRepository.findByEntityName(entityName)
                                        .orElseThrow();

        List<EntitySqlQuery> entitySqlQueryList = entity.generateQuery(entityConfig, params);

        SqlExecutor sqlExecutor = SqlExecutor.createSqlExecutor(entity.getDataSourceName());

        Map<String, List<Map<String, Object>>> results = new HashMap<>();

        for(EntitySqlQuery entitySqlQuery : entitySqlQueryList) {
            List<Map<String, Object>> resultData = sqlExecutor.execute(entitySqlQuery.getSqlQuery(), ParameterBindType.NAME_BIND);

            results.put(entitySqlQuery.getSeq(), resultData);
        }

        return EntityResult.createEntityResult(results);
    }
}
