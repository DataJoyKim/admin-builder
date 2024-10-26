package com.datajoy.web_builder.console.rest;

import com.datajoy.web_builder.apibuilder.entity.Entity;
import com.datajoy.web_builder.apibuilder.entity.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/console/api/entity")
public class EntityRestController {
    @Autowired
    private EntityRepository entityRepository;

    @GetMapping("")
    public ResponseEntity<?> getEntity() {
        List<Entity> results = entityRepository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
