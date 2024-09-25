package com.datajoy.web_builder.apibuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ApiBuilderRepository {
    private static final String DATA_DIRECTORY_PATH = "";

    private final JsonFilePersistence jsonFilePersistence;
    public ApiBuilder findApiBuilderBy(String applicationName, String path, HttpMethod method) {
        Map<String ,Object> params = new HashMap<>();

        return jsonFilePersistence.selectList(DATA_DIRECTORY_PATH, params);
    }
}
