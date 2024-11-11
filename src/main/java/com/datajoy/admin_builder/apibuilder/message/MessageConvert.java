package com.datajoy.admin_builder.apibuilder.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageConvert {

    public static List<Map<String, Object>> toArray(Object params) {
        if(params instanceof List) {
            return (List<Map<String, Object>>) params;
        }
        else if(params instanceof Map) {
            Map<String,Object> convertedParams = (Map<String,Object>) params;

            List<Map<String,Object>> results = new ArrayList<>();
            results.add(convertedParams);

            return results;
        }
        else {
            return null;
        }
    }
}
