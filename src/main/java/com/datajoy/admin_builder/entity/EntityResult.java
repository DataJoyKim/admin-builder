package com.datajoy.admin_builder.entity;

import com.datajoy.admin_builder.entity.code.EntityResultCode;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class EntityResult {
    private EntityResultCode resultCode;
    private List<Map<String,Object>> results;

    public static EntityResult createEntityResult(EntityResultCode resultCode, List<Map<String,Object>> results) {
        return EntityResult.builder()
                .resultCode(resultCode)
                .results(results)
                .build();
    }
}
