package com.datajoy.web_builder.apibuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@Slf4j
public class JsonRepository {

    public <T> T read(String path) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(path)));
            log.info("jsonData: "+jsonString);

            return  (T) objectMapper.readValue(jsonString, Object.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String path) {
    }
}
