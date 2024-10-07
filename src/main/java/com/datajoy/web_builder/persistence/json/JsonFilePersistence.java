package com.datajoy.web_builder.persistence.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonFilePersistence {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String repositoryPath;

    private String getFilePath(String filename) {
        return repositoryPath + File.separator + filename + ".json";
    }

    public <T> T selectOne(String filename, Map<String ,Object> params, TypeReference<T> typeReference) {
        List<Map<String, Object>> results = select(getFilePath(filename), params);

        Map<String, Object> result = results.get(0);

        return objectMapper.convertValue(result, typeReference);
    }

    public <T> List<T> selectList(String filename, Map<String ,Object> params, TypeReference<List<T>> typeReference) {
        List<Map<String, Object>> results = select(getFilePath(filename), params);

        return objectMapper.convertValue(results, typeReference);
    }

    public <T> void insert(String filename, T params, PrimaryKey primaryKey) {
        JSONArray savedJsonArray = getJsonData(getFilePath(filename));
        JSONParser parser = new JSONParser();

        JSONObject paramsJsonObject = toJsonObject(params, parser);

        if(primaryKey.getAutoIncrement()){
            Long generatedValue = autoIncrement(getFilePath(filename), primaryKey.getKey());
            paramsJsonObject.put(primaryKey.getKey(), generatedValue);
        }

        validateDuplicate(primaryKey.getKey(), savedJsonArray, paramsJsonObject);

        savedJsonArray.add(paramsJsonObject);

        persist(getFilePath(filename), savedJsonArray);
    }

    private Long autoIncrement(String path, String primaryKey) {

        List<Map<String, Object>> results = select(path, new HashMap<>());

        List<Long> primaryKeys = new ArrayList<>();
        for(Map<String, Object> r : results) {
            Object valueObj = r.get(primaryKey);
            if(valueObj == null) {
                continue;
            }

            Long primaryKeyValue = Long.valueOf(valueObj.toString());

            primaryKeys.add(primaryKeyValue);
        }

        if(primaryKeys.isEmpty()) {
            return 1L;
        }
        else {
            Long maxKey = primaryKeys.stream().max(Long::compare).get();

            return maxKey+1L;
        }
    }

    private void validateDuplicate(String primaryKey, JSONArray savedJsonArray, JSONObject paramsJsonObject) {
        String paramsKeyValue = createKeyValue(primaryKey, paramsJsonObject);

        for (Object o : savedJsonArray) {
            JSONObject savedJsonObject = (JSONObject) o;

            String savedKeyValue = createKeyValue(primaryKey, savedJsonObject);

            if (savedKeyValue.equals(paramsKeyValue)) {
                throw new DuplicateException();
            }
        }
    }

    public <T> void update(String filename, T params, PrimaryKey primaryKey) {
        JSONArray savedJsonArray = getJsonData(getFilePath(filename));

        JSONParser parser = new JSONParser();

        JSONObject paramsJsonObject = toJsonObject(params, parser);

        String paramsKeyValue = createKeyValue(primaryKey.getKey(), paramsJsonObject);

        for(int i=0; i<savedJsonArray.size(); i++) {
            JSONObject savedJsonObject = (JSONObject) savedJsonArray.get(i);

            String savedKeyValue = createKeyValue(primaryKey.getKey(), savedJsonObject);

            if(savedKeyValue.equals(paramsKeyValue)) {
                savedJsonArray.set(i, paramsJsonObject);
            }
        }

        persist(getFilePath(filename), savedJsonArray);
    }

    private List<Map<String, Object>> select(String path, Map<String, Object> params) {
        List<Map<String, Object>> results = new ArrayList<>();

        JSONArray jsonArray = getJsonData(path);

        if(params.isEmpty()) {
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                try {
                    results.add(objectMapper.readValue(jsonObject.toString(), new TypeReference<>() {}));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;

                boolean hasData = false;
                for (Object keyObj : jsonObject.keySet()) {
                    String key = (String) keyObj;

                    String valueStr = (String) params.get(key);

                    String jsonValueStr = String.valueOf(jsonObject.get(key));

                    if ((valueStr == null && jsonValueStr == null) || (valueStr != null && valueStr.equals(jsonValueStr))) {
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

    private void persist(String path, JSONArray jsonArray) {
        try {

            FileWriter fileWriter = new FileWriter(path);

            fileWriter.append(jsonArray.toJSONString());

            fileWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> JSONObject toJsonObject(T params, JSONParser parser) {
        try {
            return (JSONObject) parser.parse(objectMapper.writeValueAsString(params));
        }
        catch (ParseException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String createKeyValue(String primaryKey, JSONObject jsonObject) {
        Object value = jsonObject.get(primaryKey);
        if(value == null) {
            return null;
        }

        return String.valueOf(value);
    }
}
