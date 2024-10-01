package com.datajoy.persistence;

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

    public <T> void insert(String path, T params, List<String> keys) {
        JSONArray savedJsonArray = getJsonData(path);

        ObjectMapper objectMapper = new ObjectMapper();
        JSONParser parser = new JSONParser();

        JSONObject paramsJsonObject = toJsonObject(params, objectMapper, parser);

        String paramsKeyValue = createKeyValue(keys, paramsJsonObject);

        for (Object o : savedJsonArray) {
            JSONObject savedJsonObject = (JSONObject) o;

            String savedKeyValue = createKeyValue(keys, savedJsonObject);

            if (savedKeyValue.equals(paramsKeyValue)) {
                throw new DuplicateException();
            }
        }

        savedJsonArray.add(paramsJsonObject);

        write(path, savedJsonArray);
    }

    private void write(String path, JSONArray jsonArray) {
        try {

            FileWriter fileWriter = new FileWriter(path);

            fileWriter.append(jsonArray.toJSONString());

            fileWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void update(String path, T params, List<String> keys) {
        JSONArray savedJsonArray = getJsonData(path);

        ObjectMapper objectMapper = new ObjectMapper();
        JSONParser parser = new JSONParser();

        JSONObject paramsJsonObject = toJsonObject(params, objectMapper, parser);

        String paramsKeyValue = createKeyValue(keys, paramsJsonObject);

        for(int i=0; i<savedJsonArray.size(); i++) {
            JSONObject savedJsonObject = (JSONObject) savedJsonArray.get(i);

            String savedKeyValue = createKeyValue(keys, savedJsonObject);

            if(savedKeyValue.equals(paramsKeyValue)) {
                savedJsonArray.set(i, paramsJsonObject);
            }
        }

        write(path, savedJsonArray);
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

                if((valueStr == null && jsonValueStr == null) || (valueStr != null && valueStr.equals(jsonValueStr))) {
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

    private <T> JSONObject toJsonObject(T params, ObjectMapper objectMapper, JSONParser parser) {
        try {
            return (JSONObject) parser.parse(objectMapper.writeValueAsString(params));
        }
        catch (ParseException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String createKeyValue(List<String> keys, JSONObject jsonObject) {
        StringBuilder keyValue = new StringBuilder();
        int idx = 0;
        for(String key : keys) {
            keyValue.append((idx > 0) ? "-" : "").append(jsonObject.get(key));
            idx++;
        }

        return keyValue.toString();
    }
}
