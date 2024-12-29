package com.datajoy.admin_builder.apibuilder.entity;

import com.datajoy.admin_builder.apibuilder.sql.SqlExecutor;
import com.datajoy.admin_builder.apibuilder.sql.parameterbind.ParameterBindType;
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
            List<Map<String, Object>> resultData = new ArrayList<>();
            try {
                resultData = sqlExecutor.execute(entitySqlQuery.getSqlQuery(), ParameterBindType.NAME_BIND);
            }
            catch (SQLException e) {
                log.error("error",e);
                Map<String, Object> error = new HashMap<>();
                error.put("errorCode", e.getErrorCode());
                error.put("message",e.getMessage());
                resultData.add(error);
            }

            results.put(entitySqlQuery.getSeq(), resultData);
        }

        return EntityResult.createEntityResult(results);
    }
}
