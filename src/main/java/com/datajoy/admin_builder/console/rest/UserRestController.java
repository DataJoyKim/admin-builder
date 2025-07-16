package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.user.User;
import com.datajoy.admin_builder.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/console/api/user")
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> getList() {
        List<User> results = userRepository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        User results = userRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
