
package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.restclient.RestClientQueryParam;
import com.datajoy.admin_builder.restclient.code.ValueType;
import com.datajoy.admin_builder.console.repository.ConsoleRestClientQueryParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/rest-client/query-param")
public class RestClientQueryParamRestController {
    @Autowired
    private ConsoleRestClientQueryParamRepository queryParamRepository;

    @PostMapping("")
    public ResponseEntity<?> createQueryParam(@RequestBody Map<String,Object> params) {
        queryParamRepository.insert(
                Long.valueOf((String) params.get("clientId")),
                (String) params.get("paramName"),
                ValueType.valueOf((String) params.get("valueType")),
                (String) params.get("inputValue")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getQueryParam(@PathVariable("clientId") Long clientId) {
        List<RestClientQueryParam> results = queryParamRepository.findByClientId(clientId);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQueryParam(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        RestClientQueryParam queryParam = queryParamRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        queryParamRepository.update(
                queryParam.getId(),
                (String) params.get("paramName"),
                ValueType.valueOf((String) params.get("valueType")),
                (String) params.get("inputValue")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQueryParam(@PathVariable("id") Long id) {
        RestClientQueryParam queryParam = queryParamRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        queryParamRepository.deleteById(queryParam.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
