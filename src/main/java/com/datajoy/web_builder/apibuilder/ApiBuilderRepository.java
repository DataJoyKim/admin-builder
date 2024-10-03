package com.datajoy.web_builder.apibuilder;

import com.datajoy.web_builder.persistence.json.JsonFilePersistence;
import com.datajoy.web_builder.persistence.json.PrimaryKey;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ApiBuilderRepository {
    private static final String FILENAME = "api_builder";
    private final JsonFilePersistence jsonFilePersistence;

    public ApiBuilder findBy(String applicationName, Long id) {
        Map<String ,Object> params = new HashMap<>();
        params.put("id", id);
        params.put("applicationName", applicationName);
        return jsonFilePersistence.selectOne(FILENAME, params, new TypeReference<>() {});
    }

    public List<ApiBuilder> findBy(String applicationName) {
        Map<String ,Object> params = new HashMap<>();
        params.put("applicationName", applicationName);
        return jsonFilePersistence.selectList(FILENAME, params, new TypeReference<>() {});
    }

    public ApiBuilder findBy(String applicationName, String path, HttpMethod method) {
        Map<String ,Object> params = new HashMap<>();
        params.put("applicationName", applicationName);
        params.put("path", path);
        params.put("method", method.name());

        return jsonFilePersistence.selectOne(FILENAME, params, new TypeReference<>() {});
    }
    public List<ApiBuilder> findAll() {
        return jsonFilePersistence.selectList(FILENAME, new HashMap<>(), new TypeReference<>() {});
    }

    public void save(ApiBuilder params) {
        PrimaryKey primaryKey = PrimaryKey.builder()
                .key("id")
                .autoIncrement(true)
                .build();

        if(params.getId() == null) {
            jsonFilePersistence.insert(FILENAME, params, primaryKey);
        }
        else {
            jsonFilePersistence.update(FILENAME, params, primaryKey);
        }
    }
}
