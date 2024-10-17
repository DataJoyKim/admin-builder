package com.datajoy.web_builder.apibuilder.entity;

import com.datajoy.web_builder.apibuilder.entity.repository.EntityRepository;
import com.datajoy.web_builder.apibuilder.sql.SqlQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityService {
    private final EntityRepository entityRepository;
    private final EntityConfig entityConfig;

    public EntityResult execute(String entityName, EntityParameter params) {
        Entity entity = entityRepository.findByEntityName(entityName)
                                        .orElseThrow();
        
        //TODO 파라미터 바인딩
        
        //TODO 쿼리 생성
        List<SqlQuery> sqlQueryList =  entity.generateQuery(entityConfig, params);



        return null;
    }
}
