package com.datajoy.web_builder.apibuilder.entity;

import com.datajoy.web_builder.apibuilder.entity.repository.EntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityService {
    private final EntityRepository entityRepository;

    public EntityResult execute(String entityName, EntityParameter params) {
        Entity entity = entityRepository.findByEntityName(entityName)
                                        .orElseThrow();
        
        //TODO 파라미터 바인딩
        
        //TODO 쿼리 생성



        return null;
    }
}
