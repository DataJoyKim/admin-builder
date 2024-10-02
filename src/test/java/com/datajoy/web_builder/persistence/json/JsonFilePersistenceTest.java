package com.datajoy.web_builder.persistence.json;

import com.datajoy.web_builder.apibuilder.ApiBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JsonFilePersistenceTest {
    JsonFilePersistence jsonFilePersistence = new JsonFilePersistence("C:\\web-builder\\data");

    @Test
    public void selectOneTest() {
        Map<String ,Object> params = new HashMap<>();
        params.put("applicationName", "netmarble-ehr");
        params.put("path", "/api/goals");
        params.put("method", "POST");

        ApiBuilder apiBuilder = jsonFilePersistence.selectOne("api_builder", params, new TypeReference<>(){});

        Assertions.assertNotNull(apiBuilder);
    }

    @Test
    public void selectListTest() {
        Map<String ,Object> params = new HashMap<>();
        params.put("applicationName", "netmarble-ehr");

        List<ApiBuilder> apiBuilders = jsonFilePersistence.selectList("api_builder", params, new TypeReference<>(){});

        Assertions.assertNotNull(apiBuilders);
    }

    @Test
    public void insertTest() {
        ApiBuilder params = ApiBuilder.builder()
                .applicationName("netmarble-ehr")
                .id(5L)
                .path("/api/goals")
                .useAuth(true)
                .commandBeanName("com.datajoy")
                .method("GET")
                .build();

        PrimaryKey primaryKey = PrimaryKey.builder()
                .key("id")
                .autoIncrement(true)
                .build();

        jsonFilePersistence.insert("api_builder", params, primaryKey);
    }

    @Test
    public void updateTest() {
        ApiBuilder params = ApiBuilder.builder()
                .applicationName("netmarble-ehr")
                .id(5L)
                .path("/api/goals")
                .useAuth(false)
                .commandBeanName("com.datajoy.update")
                .method("POST")
                .build();

        PrimaryKey primaryKey = PrimaryKey.builder().key("id").build();

        jsonFilePersistence.update("api_builder", params, primaryKey);
    }
}