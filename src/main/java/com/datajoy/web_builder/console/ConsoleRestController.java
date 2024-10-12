package com.datajoy.web_builder.console;

import com.datajoy.web_builder.apibuilder.datasource.DataSource;
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
        List<DataSource> results = dataSourceRepository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/dataSource/{id}")
    public ResponseEntity<?> getDataSource(@PathVariable("id") Long id) {
        DataSource results = dataSourceRepository.findById(id)
                                        .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/dataSource")
    public ResponseEntity<?> postDataSource(
            @RequestBody Map<String,Object> params
    ) {
        DataSource dataSource = DataSource.createDataSource(params);

        DataSource results = dataSourceRepository.save(dataSource);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/dataSource/{id}")
    public ResponseEntity<?> putDataSource(
            @PathVariable("id") Long id,
            @RequestBody Map<String,Object> params
    ) {
        DataSource dataSource = dataSourceRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        dataSource.update(params);

        DataSource results = dataSourceRepository.save(dataSource);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
    @DeleteMapping("/dataSource/{id}")
    public ResponseEntity<?> deleteDataSource(
            @PathVariable("id") Long id
    ) {
        DataSource dataSource = dataSourceRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        dataSourceRepository.deleteById(dataSource.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
