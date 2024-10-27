package com.datajoy.web_builder.console.rest;

import com.datajoy.web_builder.apibuilder.entity.Entity;
import com.datajoy.web_builder.console.repository.ConsoleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/entity")
public class EntityRestController {
    @Autowired
    private ConsoleEntityRepository entityRepository;

    @GetMapping("")
    public ResponseEntity<?> getEntity() {
        List<Entity> results = entityRepository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createEntity(@RequestBody Map<String,Object> params) {

        Entity entity = Entity.builder()
                .entityName((String) params.get("entityName"))
                .displayName((String) params.get("displayName"))
                .dataSourceName((String) params.get("dataSourceName"))
                .tableName((String) params.get("tableName"))
                .build();

        Entity results = entityRepository.save(entity);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEntity(@PathVariable("id") Long id) {
        Entity results = entityRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        Entity entity = entityRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        entityRepository.update(
                entity.getId(),
                (String) params.get("entityName"),
                (String) params.get("displayName"),
                (String) params.get("dataSourceName"),
                (String) params.get("tableName")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable("id") Long id) {
        Entity entity = entityRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        entityRepository.deleteById(entity.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
