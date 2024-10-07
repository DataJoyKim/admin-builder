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

    public List<Map<String, Object>> findBy() {
        Map<String ,Object> params = new HashMap<>();
        return jsonFilePersistence.selectList(FILENAME, params, new TypeReference<>() {});
    }

    public void save(Map<String, Object> params) {
        PrimaryKey primaryKey = PrimaryKey.builder()
                .key("id")
                .autoIncrement(true)
                .build();

        Long id = (Long) params.get("id");

        if(id == null) {
            jsonFilePersistence.insert(FILENAME, params, primaryKey);
        }
        else {
            jsonFilePersistence.update(FILENAME, params, primaryKey);
        }
    }
}
