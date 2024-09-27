package com.datajoy.web_builder.apibuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class JsonFilePersistenceTest {
    private final String filePath = "C:\\web-builder\\data\\api_builder.json";

    @Autowired
    JsonFilePersistence jsonFilePersistence;

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
    public void writeTest() {
    }
}