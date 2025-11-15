package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.user.UserGroup;
import com.datajoy.admin_builder.user.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController("console.UserGroupRestController")
@RequestMapping("/console/api/user-group")
public class UserGroupRestController {
    @Autowired
    private UserGroupRepository repository;

    @GetMapping("")
    public ResponseEntity<?> getList() {
        List<UserGroup> results = repository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        UserGroup results = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String,Object> params) {

        UserGroup createdData = UserGroup.builder()
                .code((String) params.get("code"))
                .name((String) params.get("name"))
                .build();

        return new ResponseEntity<>(repository.save(createdData), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        UserGroup savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        savedData.update(
                (String) params.get("code"),
                (String) params.get("name")
        );

        repository.save(savedData);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        UserGroup savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.deleteById(savedData.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
