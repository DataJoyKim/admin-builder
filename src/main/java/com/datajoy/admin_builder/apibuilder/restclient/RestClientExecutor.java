package com.datajoy.admin_builder.apibuilder.restclient;

import com.datajoy.admin_builder.apibuilder.datasource.LookupKey;
import com.datajoy.admin_builder.apibuilder.datasource.restserver.DataSourceRestServerRegister;
import com.datajoy.admin_builder.apibuilder.restclient.code.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientExecutor {

    public ResponseEntity<Object> execute(RestClientExecutorRequest request) {

        RestClient restClient = DataSourceRestServerRegister.getDataSource(LookupKey.generateKey(request.getDataSource()));

        ResponseEntity<Object> response = null;

        if(HttpMethod.GET.equals(request.getMethod())) {
            response = restClient.get()
                    .uri(request::createUri)
                    .headers(request::createHeaders)
                    .retrieve()
                    .toEntity(Object.class);
        }
        else if(HttpMethod.POST.equals(request.getMethod())) {
            response = restClient.post()
                    .uri(request::createUri)
                    .headers(request::createHeaders)
                    .contentType(request.getMediaType())
                    .body(request.getRequestBody())
                    .retrieve()
                    .toEntity(Object.class);
        }
        else if(HttpMethod.PUT.equals(request.getMethod())) {
            response = restClient.put()
                    .uri(request::createUri)
                    .headers(request::createHeaders)
                    .contentType(request.getMediaType())
                    .body(request.getRequestBody())
                    .retrieve()
                    .toEntity(Object.class);
        }
        else if(HttpMethod.DELETE.equals(request.getMethod())) {
            response = restClient.delete()
                    .uri(request::createUri)
                    .headers(request::createHeaders)
                    .retrieve()
                    .toEntity(Object.class);
        }

        return response;
    }
}
