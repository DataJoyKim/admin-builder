package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.view.ViewObjectRepository;
import com.datajoy.admin_builder.view.code.ObjectType;
import com.datajoy.admin_builder.view.domain.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/object")
public class ConsoleViewObjectRestController {
    @Autowired
    private ViewObjectRepository repository;

    @GetMapping("")
    public ResponseEntity<?> getList() {
        List<ViewObject> results = repository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        ViewObject results = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String,Object> params) {

        ViewObject createdData = ViewObject.builder()
                .objectCode((String) params.get("objectCode"))
                .objectName((String) params.get("objectName"))
                .type(ObjectType.valueOf((String) params.get("type")))
                .path((String) params.get("path"))
                .useAuthValidation(Boolean.valueOf((String) params.get("useAuthValidation")))
                .useAuthorityValidation(Boolean.valueOf((String) params.get("useAuthorityValidation")))
                .build();

        return new ResponseEntity<>(repository.save(createdData), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        ViewObject savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        savedData.update(
                (String) params.get("objectCode"),
                (String) params.get("objectName"),
                ObjectType.valueOf((String) params.get("type")),
                (String) params.get("path"),
                Boolean.valueOf((String) params.get("useAuthValidation")),
                Boolean.valueOf((String) params.get("useAuthorityValidation"))
        );

        repository.save(savedData);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        ViewObject savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.deleteById(savedData.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
