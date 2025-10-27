package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.query.Query;
import com.datajoy.admin_builder.query.QueryRequest;
import com.datajoy.admin_builder.query.QueryResult;
import com.datajoy.admin_builder.query.QueryService;
import com.datajoy.admin_builder.console.repository.ConsoleQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/query")
public class QueryRestController {
    @Autowired
    private ConsoleQueryRepository queryRepository;
    @Autowired
    private QueryService queryService;

    @GetMapping("")
    public ResponseEntity<?> getQuery(
            @RequestParam(name = "queryName", required = false) String queryName
    ) {
        List<Query> results;
        if(queryName != null) {
            results = queryRepository.findByQueryName(queryName);
        }
        else {
            results = queryRepository.findAll();
        }

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuery(@PathVariable("id") Long id) {
        Query results = queryRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createQuery(@RequestBody Map<String,Object> params) {

        Query query = Query.builder()
                .queryName((String) params.get("queryName"))
                .displayName((String) params.get("displayName"))
                .dataSourceName((String) params.get("dataSourceName"))
                .query((String) params.get("query"))
                .build();

        Query results = queryRepository.save(query);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuery(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        Query query = queryRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        queryRepository.update(
                query.getId(),
                (String) params.get("queryName"),
                (String) params.get("displayName"),
                (String) params.get("dataSourceName"),
                (String) params.get("query")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuery(@PathVariable("id") Long id) {
        Query query = queryRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        queryRepository.deleteById(query.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{queryName}/execute")
    public ResponseEntity<?> executeQuery(
            @PathVariable("queryName") String queryName,
            @RequestParam Map<String, Object> params
    ) {
        QueryRequest request = QueryRequest.builder()
                .contents(params)
                .build();

        QueryResult results = queryService.execute(queryName, request);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
