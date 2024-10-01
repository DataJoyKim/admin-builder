package com.datajoy.persistence;

import com.datajoy.web_builder.apibuilder.ApiBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JsonFilePersistenceTest {
    private final String filePath = "C:\\web-builder\\data\\api_builder.json";

    JsonFilePersistence jsonFilePersistence = new JsonFilePersistence();

    @Test
    public void selectOneTest() {
        Map<String ,Object> params = new HashMap<>();
        params.put("applicationName", "netmarble-ehr");
        params.put("path", "/api/goals");
        params.put("method", "POST");

        ApiBuilder apiBuilder = jsonFilePersistence.selectOne(filePath, params, new TypeReference<>(){});

        Assertions.assertNotNull(apiBuilder);
    }

    @Test
    public void selectListTest() {
        Map<String ,Object> params = new HashMap<>();
        params.put("applicationName", "netmarble-ehr");

        List<ApiBuilder> apiBuilders = jsonFilePersistence.selectList(filePath, params, new TypeReference<>(){});

        Assertions.assertNotNull(apiBuilders);
    }

    @Test
    public void insertTest() {
        ApiBuilder params = ApiBuilder.builder()
                .applicationName("netmarble-ehr")
                .id(4L)
                .path("/api/goals")
                .useAuth(true)
                .commandBeanName("com.datajoy")
                .method("GET")
                .build();

        jsonFilePersistence.insert(filePath, params, List.of("id"));
    }

    @Test
    public void updateTest() {
        ApiBuilder params = ApiBuilder.builder()
                .applicationName("netmarble-ehr")
                .id(3L)
                .path("/api/goals")
                .useAuth(false)
                .commandBeanName("com.datajoy.update")
                .method("POST")
                .build();

        List<String> keys = List.of("id");

        jsonFilePersistence.update(filePath, params, keys);
    }
}