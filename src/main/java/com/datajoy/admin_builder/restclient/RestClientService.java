package com.datajoy.admin_builder.restclient;

import com.datajoy.admin_builder.executor.rest.RestExecutor;
import com.datajoy.admin_builder.executor.rest.RestExecutorRequest;
import com.datajoy.admin_builder.executor.rest.RestExecutorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class RestClientService {
    private final RestClientRepository restClientRepository;

    public RestClientResult execute(String clientName, RestClientRequest params) {
        RestClient clientMeta = restClientRepository.findByClientName(clientName)
                            .orElseThrow();

        RestExecutorRequest request = clientMeta.createRequest(params);

        try {
            RestExecutor restExecutor = RestExecutor.createRestClientExecutor(clientMeta.getDataSourceName());

            RestExecutorResponse response = restExecutor.execute(request);

            return RestClientResult.builder()
                    .headers(response.getHeaders())
                    .body(response.getBody())
                    .statusCode(response.getStatus())
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
