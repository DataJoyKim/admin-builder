package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.workflow.Workflow;
import com.datajoy.admin_builder.workflow.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/workflow")
public class WorkflowRestController {
    @Autowired
    private WorkflowRepository repository;

    @GetMapping("")
    public ResponseEntity<?> get() {
        List<Workflow> results = repository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Workflow results = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String,Object> params) {

        Workflow workflowBuilder = Workflow.builder()
                .workflowCode((String) params.get("workflowName"))
                .displayName((String) params.get("displayName"))
                .note((String) params.get("note"))
                .useAuthValidation(Boolean.valueOf((String) params.get("useAuthValidation")))
                .build();

        Workflow results = repository.save(workflowBuilder);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        Workflow workflowBuilder = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        workflowBuilder.update(
                (String) params.get("workflowName"),
                (String) params.get("displayName"),
                (String) params.get("note"),
                Boolean.valueOf((String) params.get("useAuthValidation"))
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable("id") Long id) {
        Workflow workflowBuilder = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.deleteById(workflowBuilder.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
