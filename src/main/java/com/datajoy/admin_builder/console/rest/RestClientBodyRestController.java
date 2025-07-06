
package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.restclient.RestClientBody;
import com.datajoy.admin_builder.restclient.code.MessageDataType;
import com.datajoy.admin_builder.restclient.code.ValueType;
import com.datajoy.admin_builder.console.repository.ConsoleRestClientBodyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/rest-client/body")
public class RestClientBodyRestController {
    @Autowired
    private ConsoleRestClientBodyRepository restClientBodyRepository;

    @PostMapping("")
    public ResponseEntity<?> createRestClientBody(@RequestBody Map<String,Object> params) {
        restClientBodyRepository.insert(
                Long.valueOf((String) params.get("clientId")),
                (String) params.get("paramName"),
                (String) params.get("parentParamName"),
                MessageDataType.valueOf((String) params.get("dataType")),
                ValueType.valueOf((String) params.get("valueType")),
                (Integer) params.get("orderNum"),
                (String) params.get("inputValue")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getRestClientBody(@PathVariable("clientId") Long clientId) {
        List<RestClientBody> results = restClientBodyRepository.findByClientId(clientId);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestClientBody(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        RestClientBody restClientBody = restClientBodyRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        restClientBodyRepository.update(
                restClientBody.getId(),
                (String) params.get("paramName"),
                (String) params.get("parentParamName"),
                MessageDataType.valueOf((String) params.get("dataType")),
                ValueType.valueOf((String) params.get("valueType")),
                (Integer) params.get("orderNum"),
                (String) params.get("inputValue")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestClientBody(@PathVariable("id") Long id) {
        RestClientBody restClientBody = restClientBodyRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        restClientBodyRepository.deleteById(restClientBody.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
