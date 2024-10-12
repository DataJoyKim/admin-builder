package com.datajoy.web_builder.console;

import com.datajoy.web_builder.apibuilder.datasource.DataSourceMeta;
import com.datajoy.web_builder.apibuilder.datasource.DataSourceMetaRepository;
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
    private DataSourceMetaRepository dataSourceMetaRepository;

    @GetMapping("/dataSource")
    public ResponseEntity<?> getDataSource() {
        List<DataSourceMeta> results = dataSourceMetaRepository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/dataSource/{id}")
    public ResponseEntity<?> getDataSource(@PathVariable("id") Long id) {
        DataSourceMeta results = dataSourceMetaRepository.findById(id)
                                        .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/dataSource")
    public ResponseEntity<?> postDataSource(
            @RequestBody Map<String,Object> params
    ) {
        DataSourceMeta dataSource = DataSourceMeta.createDataSource(params);

        DataSourceMeta results = dataSourceMetaRepository.save(dataSource);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/dataSource/{id}")
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
    @DeleteMapping("/dataSource/{id}")
    public ResponseEntity<?> deleteDataSource(
            @PathVariable("id") Long id
    ) {
        DataSourceMeta dataSource = dataSourceMetaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        dataSourceMetaRepository.deleteById(dataSource.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
