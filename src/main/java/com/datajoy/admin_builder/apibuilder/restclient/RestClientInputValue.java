package com.datajoy.admin_builder.apibuilder.restclient;

import com.datajoy.admin_builder.apibuilder.restclient.code.ValueType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RestClientInputValue {

    public Object resolve(ValueType valueType, String key, String inputValue, Map<String,Object> params) {
        if(ValueType.PARAM_VALUE.equals(valueType)) {
            return new ResolvedValue(key, params.get(key));
        }
    }

    @Getter @AllArgsConstructor
    static class ResolvedValue {
        private String key;
        private Object value;
    }
}
