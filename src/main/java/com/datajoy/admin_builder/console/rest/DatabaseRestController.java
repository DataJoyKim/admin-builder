package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.apibuilder.datasource.ConnectValidation;
import com.datajoy.admin_builder.apibuilder.datasource.database.DataSourceDatabaseRegister;
import com.datajoy.admin_builder.apibuilder.datasource.database.DataSourceDatabaseMeta;
import com.datajoy.admin_builder.apibuilder.datasource.database.DataSourceDatabaseValidator;
import com.datajoy.admin_builder.apibuilder.datasource.database.DatabaseKind;
import com.datajoy.admin_builder.console.repository.ConsoleDatabaseMetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/datasource/database")
public class DatabaseRestController {
    @Autowired
    private ConsoleDatabaseMetaRepository databaseMetaRepository;
    @Autowired
    private DataSourceDatabaseValidator dataSourceDatabaseValidator;

    @GetMapping("")
    public ResponseEntity<?> getDataSource() {
        List<DataSourceDatabaseMeta> results = databaseMetaRepository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDataSource(@PathVariable("id") Long id) {
        DataSourceDatabaseMeta results = databaseMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createDatabase(@RequestBody Map<String,Object> params) {
        databaseMetaRepository.insert(
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDatabase(
            @PathVariable("id") Long id,
            @RequestBody Map<String,Object> params
    ) {
        DataSourceDatabaseMeta databaseMeta = databaseMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        databaseMetaRepository.update(
                databaseMeta.getId(),
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
    public ResponseEntity<?> deleteDatabase(
            @PathVariable("id") Long id
    ) {
        DataSourceDatabaseMeta dataSource = databaseMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        databaseMetaRepository.deleteById(dataSource.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/refresh")
    public ResponseEntity<?> refreshDataSource(
            @PathVariable("id") Long id
    ) {
        DataSourceDatabaseMeta metadata = databaseMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        DataSourceDatabaseRegister.registry(metadata);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }
    @GetMapping("/{id}/connect-valid")
    public ResponseEntity<?> validConnectDataSource(
            @PathVariable("id") Long id
    ) {
        DataSourceDatabaseMeta metadata = databaseMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        ConnectValidation validate = dataSourceDatabaseValidator.validateConnect(metadata, DataSourceDatabaseRegister.getDataSourceMap());

        return new ResponseEntity<>(validate, HttpStatus.OK);
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getDataSourceSummary() {
        Map<String, Object> results = new HashMap<>();

        List<DataSourceDatabaseMeta> metadata = databaseMetaRepository.findAll();

        results.put("metadata",metadata);
        results.put("dataSources", DataSourceDatabaseRegister.getDataSourceMap());

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
