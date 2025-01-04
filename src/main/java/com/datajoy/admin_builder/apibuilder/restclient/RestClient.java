package com.datajoy.admin_builder.apibuilder.restclient;

import com.datajoy.admin_builder.apibuilder.restclient.code.ContentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    private String bodyMessageDataType;
    private List<RestClientBodyMessage> bodyMessages = new ArrayList<>();

    private List<RestClientHeader> headers = new ArrayList<>();

    private List<RestClientQueryParam> queryParams = new ArrayList<>();
}
