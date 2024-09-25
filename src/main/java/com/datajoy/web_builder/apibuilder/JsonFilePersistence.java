package com.datajoy.web_builder.apibuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JsonFilePersistence {

    public <T> T selectOne(String path, Map<String ,Object> params) {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONParser parser = new JSONParser();

        FileReader reader;
        try {
            reader = new FileReader(path);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            reader.close();

            List<Map<String, Object>> results = new ArrayList<>();
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;

                boolean hasData = false;
                for (Object keyObj : jsonObject.keySet()) {
                    String key = (String) keyObj;

                    String valueStr = (String) params.get(key);

                    String jsonValueStr = (String) jsonObject.get(key);

                    if (valueStr.equals(jsonValueStr)) {
                        hasData = true;
                    }
                }

                if (hasData) {
                    results.add(objectMapper.readValue(jsonObject.toString(), new TypeReference<>() {}));
                }
            }

            Map<String, Object> result = results.get(0);

            return objectMapper.convertValue(result, new TypeReference<>() {});
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> selectList(String path, Map<String ,Object> params) {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONParser parser = new JSONParser();

        FileReader reader;
        try {
            reader = new FileReader(path);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            reader.close();

            List<Map<String, Object>> results = new ArrayList<>();
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;

                boolean hasData = false;
                for (Object keyObj : jsonObject.keySet()) {
                    String key = (String) keyObj;

                    String valueStr = (String) params.get(key);

                    String jsonValueStr = (String) jsonObject.get(key);

                    if (valueStr.equals(jsonValueStr)) {
                        hasData = true;
                    }
                }

                if (hasData) {
                    results.add(objectMapper.readValue(jsonObject.toString(), new TypeReference<>() {}));
                }
            }

            return objectMapper.convertValue(results, new TypeReference<>() {});
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String path) {
    }
}
