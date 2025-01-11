package com.datajoy.admin_builder.apibuilder.restclient;

import com.datajoy.admin_builder.apibuilder.restclient.code.MessageDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
public class RestClientBody {
    private String parameterName;
    private String parentParameterName;
    private MessageDataType dataType;
    private String autoValueType;
    private Integer orderNum;

    public static Map<String, RestClientBody> toBodyMetaMap(List<RestClientBody> bodyMeta) {
        Map<String, RestClientBody> bodyMetaMap = new HashMap<>();
        for(RestClientBody m : bodyMeta){
            bodyMetaMap.put(m.getParameterName(), m);
        }

        return bodyMetaMap;
    }
}
