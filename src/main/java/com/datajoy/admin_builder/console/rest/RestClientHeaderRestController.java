
package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.apibuilder.restclient.RestClientHeader;
import com.datajoy.admin_builder.console.repository.ConsoleRestClientHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/rest-client-header")
public class RestClientHeaderRestController {
    @Autowired
    private ConsoleRestClientHeaderRepository restClientHeaderRepository;

    @PostMapping("")
    public ResponseEntity<?> createRestClientHeader(@RequestBody Map<String,Object> params) {
        restClientHeaderRepository.insert(
                Long.valueOf((String) params.get("clientId")),
                (String) params.get("name"),
                (String) params.get("headerValue")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getRestClientHeader(@PathVariable("clientId") Long clientId) {
        List<RestClientHeader> results = restClientHeaderRepository.findByClientId(clientId);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestClientHeader(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        RestClientHeader restClientHeader = restClientHeaderRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        restClientHeaderRepository.update(
                restClientHeader.getId(),
                (String) params.get("name"),
                (String) params.get("headerValue")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestClientHeader(@PathVariable("id") Long id) {
        RestClientHeader restClientHeader = restClientHeaderRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        restClientHeaderRepository.deleteById(restClientHeader.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
