package com.datajoy.admin_builder.apibuilder.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestClientService {
    private final RestClientRepository restClientRepository;
    private final RestClientExecutor restClientExecutor;

    public RestClientResult execute(String clientName, RestClientRequest params) {
        RestClient clientMeta = restClientRepository.findByClientName(clientName)
                            .orElseThrow();

        ResponseEntity<Object> response = restClientExecutor.execute(clientMeta, params);

        return RestClientResult.builder()
                .response(response)
                .build();
    }
}
