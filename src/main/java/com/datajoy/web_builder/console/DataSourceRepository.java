package com.datajoy.web_builder.console;

import com.datajoy.web_builder.persistence.json.JsonFilePersistence;
import com.datajoy.web_builder.persistence.json.PrimaryKey;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
@RequiredArgsConstructor
public class DataSourceRepository {
    private static final String FILENAME = "data_source";
    private final JsonFilePersistence jsonFilePersistence;

    public void insert(Map<String, Object> params) {
        PrimaryKey primaryKey = PrimaryKey.builder()
                .key("id")
                .autoIncrement(true)
                .build();

        jsonFilePersistence.insert(FILENAME, params, primaryKey);
    }

    public void update(Map<String, Object> params) {
        PrimaryKey primaryKey = PrimaryKey.builder()
                .key("id")
                .build();

        jsonFilePersistence.update(FILENAME, params, primaryKey);
    }

    public List<Map<String, Object>> findBy(Long id) {
        Map<String ,Object> params = new HashMap<>();
        
        if(id != null) {
            params.put("id",id);
        }
        return jsonFilePersistence.selectList(FILENAME, params, new TypeReference<>() {});
    }
}
