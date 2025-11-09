package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.restclient.RestClient;
import com.datajoy.admin_builder.restclient.RestClientRequest;
import com.datajoy.admin_builder.restclient.RestClientResult;
import com.datajoy.admin_builder.restclient.RestClientService;
import com.datajoy.admin_builder.restclient.code.BodyMessageFormat;
import com.datajoy.admin_builder.restclient.code.ContentType;
import com.datajoy.admin_builder.restclient.code.HttpMethod;
import com.datajoy.admin_builder.console.repository.ConsoleRestClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/rest-client")
public class RestClientRestController {
    @Autowired
    private ConsoleRestClientRepository restClientRepository;
    @Autowired
    private RestClientService restClientService;

    @GetMapping("")
    public ResponseEntity<?> getRestClient(
            @RequestParam(name = "clientName", required = false) String clientName
    ) {
        List<RestClient> results;
        if(clientName != null) {
            results = restClientRepository.findByClientName(clientName);
        }
        else {
            results = restClientRepository.findAll();
        }

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestClient(@PathVariable("id") Long id) {
        RestClient results = restClientRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createRestClient(@RequestBody Map<String,Object> params) {
        String contentType = (String) params.get("contentType");
        String bodyMessageFormat = (String) params.get("bodyMessageFormat");

        RestClient restClient = RestClient.builder()
                .clientName((String) params.get("clientName"))
                .displayName((String) params.get("displayName"))
                .dataSourceName((String) params.get("dataSourceName"))
                .method(HttpMethod.valueOf((String) params.get("method")))
                .path((String) params.get("path"))
                .contentType((contentType != null && !contentType.isEmpty()) ? ContentType.valueOf((String) params.get("contentType")) : null)
                .bodyMessageFormat((bodyMessageFormat != null && !bodyMessageFormat.isEmpty()) ? BodyMessageFormat.valueOf((String) params.get("bodyMessageFormat")) : null)
                .build();

        RestClient results = restClientRepository.save(restClient);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestClient(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        RestClient results = restClientRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        String contentType = (String) params.get("contentType");
        String bodyMessageFormat = (String) params.get("bodyMessageFormat");

        restClientRepository.update(
                results.getId(),
                (String) params.get("clientName"),
                (String) params.get("displayName"),
                (String) params.get("dataSourceName"),
                HttpMethod.valueOf((String) params.get("method")),
                (String) params.get("path"),
                (contentType != null && !contentType.isEmpty()) ? ContentType.valueOf((String) params.get("contentType")) : null,
                (bodyMessageFormat != null && !bodyMessageFormat.isEmpty()) ? BodyMessageFormat.valueOf((String) params.get("bodyMessageFormat")) : null
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestClient(@PathVariable("id") Long id) {
        RestClient restClient = restClientRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        restClientRepository.deleteById(restClient.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{clientName}/execute")
    public ResponseEntity<?> executeQuery(
            @PathVariable("clientName") String clientName,
            @RequestParam Map<String, Object> params,
            @RequestBody(required = false) Object requestBody
    ) {
        RestClientRequest request = RestClientRequest.builder()
                .params(params)
                .requestBody(requestBody)
                .build();

        RestClientResult results = restClientService.execute(clientName, request);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
