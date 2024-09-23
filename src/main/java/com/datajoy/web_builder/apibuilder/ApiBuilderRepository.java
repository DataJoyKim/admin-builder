package com.datajoy.web_builder.apibuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.Reader;

@Repository
@RequiredArgsConstructor
public class ApiBuilderRepository {
    private static final String DATA_DIRECTORY_PATH = "";

    private final JsonRepository jsonRepository;
    public ApiBuilder findApiBuilderBy(String applicationName, String path, HttpMethod method) {
        return jsonRepository.read(DATA_DIRECTORY_PATH);
    }
}
