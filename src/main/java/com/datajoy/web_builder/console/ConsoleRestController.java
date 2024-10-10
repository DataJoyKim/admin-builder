package com.datajoy.web_builder.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api")
public class ConsoleRestController {
    @Autowired
    private DataSourceRepository dataSourceRepository;

    @GetMapping("/dataSource")
    public ResponseEntity<?> getDataSource() {
        List<Map<String, Object>> results = dataSourceRepository.findBy(null);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/dataSource/{id}")
    public ResponseEntity<?> getDataSource(@PathVariable("id") Long id) {
        List<Map<String, Object>> results = dataSourceRepository.findBy(id);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/dataSource/{id}")
    public ResponseEntity<?> putDataSource(
            @PathVariable("id") Long id,
            @RequestBody Map<String,Object> params
    ) {
        List<Map<String, Object>> results = dataSourceRepository.findBy(id);
        if(results.isEmpty()) {
            throw new RuntimeException();
        }

        Map<String, Object> savedData = results.get(0);

        savedData.put("dataSourceName",params.get("dataSourceName"));
        savedData.put("displayName",params.get("displayName"));
        savedData.put("note",params.get("note"));

        dataSourceRepository.update(savedData);

        return new ResponseEntity<>(savedData, HttpStatus.OK);
    }

    @PostMapping("/dataSource")
    public ResponseEntity<?> postDataSource(
            @RequestBody Map<String,Object> params
    ) {
        dataSourceRepository.insert(params);

        return new ResponseEntity<>(params, HttpStatus.OK);
    }
}
