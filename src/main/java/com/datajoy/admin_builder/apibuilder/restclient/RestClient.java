package com.datajoy.admin_builder.apibuilder.restclient;

import com.datajoy.admin_builder.apibuilder.restclient.code.ContentType;
import com.datajoy.admin_builder.apibuilder.restclient.code.MessageDataType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="REST_CLIENT_UQ",columnNames={"clientName"})})
@Entity
public class RestClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String clientName;

    @Column(nullable = false, length = 200)
    private String displayName;

    @Column(nullable = false, length = 100)
    private String dataSourceName;

    private String method;

    private String path;

    private String authorization;

    private ContentType contentType;

    private String contentTypeExpend;

    private String bodyString;

    private MessageDataType bodyDataType;
    private List<RestClientBody> body = new ArrayList<>();

    private List<RestClientHeader> headers = new ArrayList<>();

    private List<RestClientQueryParam> queryParams = new ArrayList<>();

    public static Map<String, Object> createMessage(String parentParameterName, Map<String, Object> params, Map<String, List<RestClientBody>> bodyMetaByParent) {
        Map<String, Object> node = new HashMap<>();

        List<RestClientBody> bodyMeta = bodyMetaByParent.get(parentParameterName);

        Map<String, RestClientBody> bodyMetaMap = new HashMap<>();
        for(RestClientBody m : bodyMeta){
            bodyMetaMap.put(m.getParameterName(), m);
        }

        for(String key : params.keySet()) {
            if(!bodyMetaMap.containsKey(key)) {
                continue;
            }

            RestClientBody meta = bodyMetaMap.get(key);

            Object value = params.get(key);

            if(MessageDataType.ARRAY.equals(meta.getDataType())) {
                List<Map<String, Object>> childNodes = new ArrayList<>();

                List<Map<String, Object>> childParams = (List<Map<String, Object>>) value;

                for(Map<String, Object> childParam : childParams) {
                    childNodes.add(createMessage(key, childParam, bodyMetaByParent));
                }

                node.put(key, childNodes);
            }
            else if(MessageDataType.OBJECT.equals(meta.getDataType())) {
                Map<String, Object> childParam = (Map<String, Object>) value;

                node.put(key, createMessage(key, childParam, bodyMetaByParent));
            }
            else {
                node.put(key, value);
            }
        }

        return node;
    }

}
