package com.datajoy.web_builder.console;

import com.datajoy.web_builder.apibuilder.datasource.BusinessDataSource;
import com.datajoy.web_builder.apibuilder.datasource.ConnectValidation;
import com.datajoy.web_builder.apibuilder.datasource.DataSourceMeta;
import com.datajoy.web_builder.apibuilder.datasource.DataSourceMetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api")
public class ConsoleRestController {
    @Autowired
    private DataSourceMetaRepository dataSourceMetaRepository;
    @Autowired
    private BusinessDataSource businessDataSource;

    @GetMapping("/datasource")
    public ResponseEntity<?> getDataSource() {
        List<DataSourceMeta> results = dataSourceMetaRepository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/datasource/{id}")
    public ResponseEntity<?> getDataSource(@PathVariable("id") Long id) {
        DataSourceMeta results = dataSourceMetaRepository.findById(id)
                                        .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/datasource")
    public ResponseEntity<?> postDataSource(
            @RequestBody Map<String,Object> params
    ) {
        DataSourceMeta dataSource = DataSourceMeta.createDataSource(params);

        DataSourceMeta results = dataSourceMetaRepository.save(dataSource);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/datasource/summary")
    public ResponseEntity<?> getDataSourceSummary() {
        Map<String, Object> results = new HashMap<>();

        List<DataSourceMeta> metadata = dataSourceMetaRepository.findAll();

        results.put("metadata",metadata);
        results.put("dataSources",businessDataSource.getDataSourceMap());

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/datasource/{id}/refresh")
    public ResponseEntity<?> refreshDataSource(
            @PathVariable("id") Long id
    ) {
        DataSourceMeta metadata = dataSourceMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        businessDataSource.registry(metadata);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PutMapping("/datasource/{id}")
    public ResponseEntity<?> putDataSource(
            @PathVariable("id") Long id,
            @RequestBody Map<String,Object> params
    ) {
        DataSourceMeta dataSource = dataSourceMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        dataSource.update(params);

        DataSourceMeta results = dataSourceMetaRepository.save(dataSource);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
    @DeleteMapping("/datasource/{id}")
    public ResponseEntity<?> deleteDataSource(
            @PathVariable("id") Long id
    ) {
        DataSourceMeta dataSource = dataSourceMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        dataSourceMetaRepository.deleteById(dataSource.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/datasource/{id}/connect-valid")
    public ResponseEntity<?> validConnectDataSource(
            @PathVariable("id") Long id
    ) {
        DataSourceMeta metadata = dataSourceMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        ConnectValidation validate = businessDataSource.validateConnect(metadata);

        return new ResponseEntity<>(validate, HttpStatus.OK);
    }
}
