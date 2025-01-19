package com.datajoy.admin_builder.apibuilder.restclient;

import com.datajoy.admin_builder.apibuilder.restclient.code.BodyMessageFormat;
import com.datajoy.admin_builder.apibuilder.restclient.code.ContentType;
import com.datajoy.admin_builder.apibuilder.restclient.code.HttpMethod;
import com.datajoy.admin_builder.apibuilder.restclient.code.MessageDataType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
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

    @Column(nullable = false, length = 200)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ContentType contentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
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

    public Object createBody(Object requestBodyObj) {
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

    public URI createUri(UriBuilder uriBuilder, Map<String, Object> params) {
        uriBuilder.path(this.path);

        for(RestClientQueryParam q : this.queryParams) {
            uriBuilder.queryParam(q.getParamName(), params.get(q.getParamName()));
        }

        return uriBuilder.build(params);
    }

    public MediaType getMediaType() {
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

    public void createHeaders(HttpHeaders headers, Map<String, Object> params) {
        if(this.headers == null){
            return;
        }

        for(RestClientHeader h : this.headers) {
            headers.set(h.getKey(), h.getValue());
        }
    }
}
