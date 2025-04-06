package com.datajoy.admin_builder.apibuilder.restclient;

import com.datajoy.admin_builder.apibuilder.restclient.code.BodyMessageFormat;
import com.datajoy.admin_builder.apibuilder.restclient.code.ContentType;
import com.datajoy.admin_builder.apibuilder.restclient.code.HttpMethod;
import com.datajoy.admin_builder.apibuilder.restclient.code.MessageDataType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="REST_CLIENT_UQ",columnNames={"clientName"})})
@Entity
public class RestClient {
    private final String PARENT_ROOT_NAME = "ROOT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String clientName;

    @Column(nullable = false, length = 200)
    private String displayName;

    @Column(nullable = false, length = 100)
    private String dataSourceName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private HttpMethod method;

    @Column(length = 200)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ContentType contentType;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private BodyMessageFormat bodyMessageFormat;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_ID")
    private List<RestClientBody> body = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_ID")
    private List<RestClientHeader> headers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_ID")
    private List<RestClientQueryParam> queryParams = new ArrayList<>();

    public RestClientExecutorRequest createRequest(RestClientRequest params) {
        return RestClientExecutorRequest.builder()
                .requestQueryParam(createRequestQueryParams(params.getParams()))
                .requestHeaders(createRequestHeaders(params.getParams()))
                .mediaType(createMediaType())
                .requestBody(createRequestBody(params.getRequestBody()))
                .dataSource(this.dataSourceName)
                .method(method)
                .build();
    }

    private Map<String, String> createRequestHeaders(Map<String, Object> params) {
        Map<String,String> requestHeaders = new HashMap<>();

        for(RestClientHeader h : this.headers) {
            RestClientInputValue.ResolvedValue resolvedValue = RestClientInputValue.resolve(h.getValueType(), h.getName(), h.getInputValue(), params);

            requestHeaders.put(resolvedValue.getKey(), String.valueOf(resolvedValue.getValue()));
        }

        return requestHeaders;
    }

    private Map<String, Object> createRequestQueryParams(Map<String, Object> params) {
        Map<String,Object> requestQueryParam = new HashMap<>();

        for(RestClientQueryParam q : this.queryParams) {
            RestClientInputValue.ResolvedValue resolvedValue = RestClientInputValue.resolve(q.getValueType(), q.getParamName(), q.getInputValue(), params);

            requestQueryParam.put(resolvedValue.getKey(), resolvedValue.getValue());
        }

        return requestQueryParam;
    }

    private Object createRequestBody(Object requestBodyObj) {
        if(this.body == null) {
            return null;
        }

        Map<String, List<RestClientBody>> bodyMetaByParent = this.body.stream().collect(Collectors.groupingBy(RestClientBody::getParentParamName));

        if(BodyMessageFormat.ARRAY.equals(bodyMessageFormat)) {
            List<Map<String, Object>> requestBody = (List<Map<String, Object>>) requestBodyObj;

            List<Map<String, Object>> body = new ArrayList<>();
            for(Map<String, Object> r : requestBody) {
                body.add(createMessage(PARENT_ROOT_NAME, r, bodyMetaByParent));
            }

            return body;
        }
        else if(BodyMessageFormat.OBJECT.equals(bodyMessageFormat)) {
            Map<String, Object> requestBody = (Map<String, Object>) requestBodyObj;

            return createMessage(PARENT_ROOT_NAME, requestBody, bodyMetaByParent);
        }
        else {
            return null;
        }
    }

    private Map<String, Object> createMessage(
            String parentParameterName,
            Map<String, Object> params,
            Map<String, List<RestClientBody>> bodyMetaByParent
    ) {
        Map<String, Object> node = new HashMap<>();

        List<RestClientBody> bodyMeta = bodyMetaByParent.get(parentParameterName);

        Map<String, RestClientBody> bodyMetaMap = RestClientBody.toBodyMetaMap(bodyMeta);

        for(String key : params.keySet()) {
            if(!bodyMetaMap.containsKey(key)) {
                continue;
            }

            RestClientBody meta = bodyMetaMap.get(key);

            if(MessageDataType.ARRAY.equals(meta.getDataType())) {
                List<Map<String, Object>> childNodes = new ArrayList<>();

                List<Map<String, Object>> childParams = (List<Map<String, Object>>) params.get(key);

                for(Map<String, Object> childParam : childParams) {
                    childNodes.add(createMessage(key, childParam, bodyMetaByParent));
                }

                node.put(key, childNodes);
            }
            else if(MessageDataType.OBJECT.equals(meta.getDataType())) {
                Map<String, Object> childParam = (Map<String, Object>) params.get(key);

                node.put(key, createMessage(key, childParam, bodyMetaByParent));
            }
            else {
                RestClientInputValue.ResolvedValue resolvedValue = RestClientInputValue.resolve(meta.getValueType(), meta.getParamName(), meta.getInputValue(), params);

                node.put(resolvedValue.getKey(), resolvedValue.getValue());
            }
        }

        return node;
    }

    private MediaType createMediaType() {
        if(ContentType.APPLICATION_JSON.equals(this.contentType)) {
            return MediaType.APPLICATION_JSON;
        }
        else if(ContentType.TEXT_PLAIN.equals(this.contentType)) {
            return MediaType.TEXT_PLAIN;
        }
        else if(ContentType.APPLICATION_FORM_URLENCODED.equals(this.contentType)) {
            return MediaType.APPLICATION_FORM_URLENCODED;
        }
        else {
            return null;
        }
    }
}
