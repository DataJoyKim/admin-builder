package com.datajoy.admin_builder.apibuilder.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class RestClientService {
    private final RestClientRepository restClientRepository;
    private final RestClientExecutor restClientExecutor;

    public RestClientResult execute(String clientName, RestClientRequest params) {
        RestClient clientMeta = restClientRepository.findByClientName(clientName)
                            .orElseThrow();

        RestClientExecutorRequest request = clientMeta.createRequest(params);

        try {
            ResponseEntity<Object> response = restClientExecutor.execute(request);

            return RestClientResult.builder()
                    .headers(response.getHeaders())
                    .body(response.getBody())
                    .statusCode(response.getStatusCode())
                    .build();
        }
        catch (HttpClientErrorException e) {
            return RestClientResult.builder()
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString())
                    .statusCode(e.getStatusCode())
                    .build();
        }
    }
}
