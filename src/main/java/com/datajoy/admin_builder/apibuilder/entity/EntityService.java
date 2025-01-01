package com.datajoy.admin_builder.apibuilder.entity;

import com.datajoy.admin_builder.apibuilder.entity.code.EntityResultCode;
import com.datajoy.admin_builder.apibuilder.sql.SqlExecutor;
import com.datajoy.admin_builder.apibuilder.sql.parameterbind.ParameterBindType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
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

        List<EntityResult.EntityResultElement> results = new ArrayList<>();

        int failed = 0;
        for(EntitySqlQuery entitySqlQuery : entitySqlQueryList) {
            try {
                List<Map<String, Object>> resultData = sqlExecutor.execute(entitySqlQuery.getSqlQuery(), ParameterBindType.NAME_BIND);

                results.add(EntityResult.EntityResultElement.success(entitySqlQuery.getSeq(), resultData));
            }
            catch (SQLException e) {
                log.error("error",e);
                results.add(EntityResult.EntityResultElement.failed(entitySqlQuery.getSeq(), "[" +e.getErrorCode() + "] " + e.getMessage()));
                failed++;
            }
        }

        if(failed > 0) {
            return EntityResult.createEntityResult(EntityResultCode.FAILURE,results);
        }
        else {
            return EntityResult.createEntityResult(EntityResultCode.SUCCESS,results);
        }
    }
}
