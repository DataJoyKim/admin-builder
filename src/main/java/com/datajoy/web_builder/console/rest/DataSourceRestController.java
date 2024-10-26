package com.datajoy.web_builder.console.rest;

import com.datajoy.web_builder.apibuilder.datasource.BusinessDataSource;
import com.datajoy.web_builder.apibuilder.datasource.ConnectValidation;
import com.datajoy.web_builder.apibuilder.datasource.DataSourceMeta;
import com.datajoy.web_builder.apibuilder.datasource.database.DatabaseKind;
import com.datajoy.web_builder.console.repository.ConsoleDataSourceMetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/datasource")
public class DataSourceRestController {
    @Autowired
    private ConsoleDataSourceMetaRepository dataSourceMetaRepository;
    @Autowired
    private BusinessDataSource businessDataSource;

    @GetMapping("")
    public ResponseEntity<?> getDataSource() {
        List<DataSourceMeta> results = dataSourceMetaRepository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createDataSource(
            @RequestBody Map<String,Object> params
    ) {
        DataSourceMeta dataSource = DataSourceMeta.builder()
                                    .dataSourceName((String) params.get("dataSourceName"))
                                    .displayName((String) params.get("displayName"))
                                    .note((String) params.get("note"))
                                    .url((String) params.get("url"))
                                    .username((String) params.get("username"))
                                    .password((String) params.get("password"))
                                    .databaseKind(DatabaseKind.valueOf((String) params.get("databaseKind")))
                                    .maximumPoolSize(Integer.valueOf((String) params.get("maximumPoolSize")))
                                    .minimumIdle(Integer.valueOf((String) params.get("minimumIdle")))
                                    .connectionTimeout(Integer.valueOf((String) params.get("connectionTimeout")))
                                    .validationTimeout(Integer.valueOf((String) params.get("validationTimeout")))
                                    .build();

        DataSourceMeta results = dataSourceMetaRepository.save(dataSource);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDataSource(@PathVariable("id") Long id) {
        DataSourceMeta results = dataSourceMetaRepository.findById(id)
                                        .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDataSource(
            @PathVariable("id") Long id,
            @RequestBody Map<String,Object> params
    ) {
        DataSourceMeta dataSource = dataSourceMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        dataSourceMetaRepository.update(
                dataSource.getId(),
                (String) params.get("dataSourceName"),
                (String) params.get("displayName"),
                (String) params.get("note"),
                (String) params.get("url"),
                (String) params.get("username"),
                (String) params.get("password"),
                DatabaseKind.valueOf((String) params.get("databaseKind")),
                Integer.valueOf((String) params.get("maximumPoolSize")),
                Integer.valueOf((String) params.get("minimumIdle")),
                Integer.valueOf((String) params.get("connectionTimeout")),
                Integer.valueOf((String) params.get("validationTimeout"))
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDataSource(
            @PathVariable("id") Long id
    ) {
        DataSourceMeta dataSource = dataSourceMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        dataSourceMetaRepository.deleteById(dataSource.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/refresh")
    public ResponseEntity<?> refreshDataSource(
            @PathVariable("id") Long id
    ) {
        DataSourceMeta metadata = dataSourceMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        businessDataSource.registry(metadata);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }
    @GetMapping("/{id}/connect-valid")
    public ResponseEntity<?> validConnectDataSource(
            @PathVariable("id") Long id
    ) {
        DataSourceMeta metadata = dataSourceMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        ConnectValidation validate = businessDataSource.validateConnect(metadata);

        return new ResponseEntity<>(validate, HttpStatus.OK);
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getDataSourceSummary() {
        Map<String, Object> results = new HashMap<>();

        List<DataSourceMeta> metadata = dataSourceMetaRepository.findAll();

        results.put("metadata",metadata);
        results.put("dataSources",businessDataSource.getDataSourceMap());

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
