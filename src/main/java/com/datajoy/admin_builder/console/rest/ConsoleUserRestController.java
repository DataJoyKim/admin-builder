package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.user.User;
import com.datajoy.admin_builder.user.UserRepository;
import com.datajoy.core.crypto.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/user")
public class ConsoleUserRestController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public ResponseEntity<?> getList() {
        List<User> results = repository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        User results = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        User savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        savedData.update(
                (String) params.get("loginId"),
                (String) params.get("userName"),
                (String) params.get("email")
        );

        repository.save(savedData);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        User savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.deleteById(savedData.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
