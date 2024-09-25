package com.datajoy.web_builder.apibuilder;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JsonFilePersistence {

    public List<Map<String, Object>> selectList(String path, Map<String ,Object> params) {
        JSONParser parser = new JSONParser();

        FileReader reader;
        try {
            reader = new FileReader(path);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            reader.close();

            for(int i=0; i<jsonArray.size(); i++) {

            }

            return null;
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String path) {
    }
}
