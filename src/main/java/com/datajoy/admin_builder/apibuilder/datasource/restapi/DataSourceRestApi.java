package com.datajoy.admin_builder.apibuilder.datasource.restapi;

import lombok.Getter;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Getter
public class DataSourceRestApi {
    private String baseUrl;
    private Integer connectTimeout;
    private Integer connectRequestTimeout;
    private Integer connectionMaxTotal;
    private Integer connectionDefaultMaxPerRoute;

    public RestClient createDataSource() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(connectionMaxTotal);
        connectionManager.setDefaultMaxPerRoute(connectionDefaultMaxPerRoute);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setConnectionRequestTimeout(connectRequestTimeout);
        factory.setHttpClient(httpClient);

        return RestClient.builder()
                .requestFactory(factory)
                .baseUrl(baseUrl)
                .build();
    }
}
