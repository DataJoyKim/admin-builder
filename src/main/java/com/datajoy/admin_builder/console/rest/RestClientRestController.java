package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.apibuilder.restclient.RestClient;
import com.datajoy.admin_builder.apibuilder.restclient.code.BodyMessageFormat;
import com.datajoy.admin_builder.apibuilder.restclient.code.ContentType;
import com.datajoy.admin_builder.apibuilder.restclient.code.HttpMethod;
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

    @GetMapping("")
    public ResponseEntity<?> getRestClient() {
        List<RestClient> results = restClientRepository.findAll();

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

        RestClient restClient = RestClient.builder()
                .clientName((String) params.get("clientName"))
                .displayName((String) params.get("displayName"))
                .dataSourceName((String) params.get("dataSourceName"))
                .method(HttpMethod.valueOf((String) params.get("method")))
                .path((String) params.get("path"))
                .contentType(ContentType.valueOf((String) params.get("contentType")))
                .bodyMessageFormat(BodyMessageFormat.valueOf((String) params.get("bodyMessageFormat")))
                .build();

        RestClient results = restClientRepository.save(restClient);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestClient(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        RestClient results = restClientRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        restClientRepository.update(
                results.getId(),
                (String) params.get("clientName"),
                (String) params.get("displayName"),
                (String) params.get("dataSourceName"),
                HttpMethod.valueOf((String) params.get("method")),
                (String) params.get("path"),
                ContentType.valueOf((String) params.get("contentType")),
                BodyMessageFormat.valueOf((String) params.get("bodyMessageFormat"))
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
}
