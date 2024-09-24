package com.datajoy.web_builder.apibuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@Slf4j
public class JsonFilePersistence {

    public <T> T selectList(String path) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(path)));
            log.info("jsonData: "+ jsonString);

            return  (T) objectMapper.readValue(jsonString, Object.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String path) {
    }
}
