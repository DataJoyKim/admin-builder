package com.datajoy.web_builder.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api")
public class ConsoleRestController {
    @Autowired
    private DataSourceRepository dataSourceRepository;

    @GetMapping("/dataSource")
    public ResponseEntity<?> getDataSource() {
        List<Map<String, Object>> results = dataSourceRepository.findBy();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
