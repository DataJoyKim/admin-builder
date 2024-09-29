package com.datajoy.web_builder.apibuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JsonFilePersistence {

    public <T> T selectOne(String path, Map<String ,Object> params, TypeReference<T> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> results = select(path, params, objectMapper);

        Map<String, Object> result = results.get(0);

        return objectMapper.convertValue(result, typeReference);
    }

    public <T> List<T> selectList(String path, Map<String ,Object> params, TypeReference<List<T>> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> results = select(path, params, objectMapper);

        return objectMapper.convertValue(results, typeReference);
    }

    public <T> void insert(String path, T params) {
        JSONArray jsonArray = getJsonData(path);

        ObjectMapper objectMapper = new ObjectMapper();
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(objectMapper.writeValueAsString(params));

            jsonArray.add(jsonObject);

            FileWriter fileWriter = new FileWriter(path);

            fileWriter.append(jsonArray.toJSONString());

            fileWriter.close();
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Map<String, Object>> select(String path, Map<String, Object> params, ObjectMapper objectMapper) {
        JSONArray jsonArray = getJsonData(path);

        List<Map<String, Object>> results = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;

            boolean hasData = false;
            for (Object keyObj : jsonObject.keySet()) {
                String key = (String) keyObj;

                String valueStr = (String) params.get(key);

                String jsonValueStr = String.valueOf(jsonObject.get(key));

                if((valueStr == null && jsonValueStr == null)
                        || (valueStr != null && valueStr.equals(jsonValueStr))
                ) {
                    hasData = true;
                }
            }

            if (hasData) {
                try {
                    results.add(objectMapper.readValue(jsonObject.toString(), new TypeReference<>() {}));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return results;
    }

    private JSONArray getJsonData(String path) {
        JSONParser parser = new JSONParser();

        JSONArray jsonArray;
        try {
            FileReader reader = new FileReader(path);
            jsonArray = (JSONArray) parser.parse(reader);

            reader.close();
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return jsonArray;
    }
}
