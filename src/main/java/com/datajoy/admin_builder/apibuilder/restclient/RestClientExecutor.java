package com.datajoy.admin_builder.apibuilder.restclient;

import com.datajoy.admin_builder.apibuilder.datasource.LookupKey;
import com.datajoy.admin_builder.apibuilder.datasource.restserver.DataSourceRestServerRegister;
import com.datajoy.admin_builder.apibuilder.restclient.code.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RestClientExecutor {

    public ResponseEntity<Object> execute(
            RestClient clientMeta,
            RestClientRequest params
    ) {
        org.springframework.web.client.RestClient restClient = DataSourceRestServerRegister.getDataSource(LookupKey.generateKey(clientMeta.getDataSourceName()));

        ResponseEntity<Object> response = null;

        if(HttpMethod.GET.equals(clientMeta.getMethod())) {
            response = restClient.get()
                    .uri(uriBuilder -> clientMeta.createUri(uriBuilder, params.getParams()))
                    .headers(headers -> clientMeta.createHeaders(headers, params.getParams()))
                    .retrieve()
                    .toEntity(Object.class);
        }
        else if(HttpMethod.POST.equals(clientMeta.getMethod())) {
            response = restClient.post()
                    .uri(uriBuilder -> clientMeta.createUri(uriBuilder, params.getParams()))
                    .headers(headers -> clientMeta.createHeaders(headers, params.getParams()))
                    .contentType(clientMeta.getMediaType())
                    .body(clientMeta.createBody(params.getRequestBody()))
                    .retrieve()
                    .toEntity(Object.class);
        }

        return response;
    }
}
