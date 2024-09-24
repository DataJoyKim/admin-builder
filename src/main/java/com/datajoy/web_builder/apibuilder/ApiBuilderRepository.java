package com.datajoy.web_builder.apibuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApiBuilderRepository {
    private static final String DATA_DIRECTORY_PATH = "";

    private final JsonFilePersistence jsonFilePersistence;
    public ApiBuilder findApiBuilderBy(String applicationName, String path, HttpMethod method) {
        return jsonFilePersistence.selectList(DATA_DIRECTORY_PATH);
    }
}
