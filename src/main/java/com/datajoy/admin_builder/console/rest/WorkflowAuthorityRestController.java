package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.workflow.Workflow;
import com.datajoy.admin_builder.workflow.WorkflowAuthority;
import com.datajoy.admin_builder.workflow.WorkflowAuthorityRepository;
import com.datajoy.admin_builder.workflow.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController("console.WorkflowAuthorityRestController")
@RequestMapping("/console/api/workflow-authority")
public class WorkflowAuthorityRestController {
    @Autowired
    private WorkflowAuthorityRepository repository;
    @Autowired
    private WorkflowRepository workflowRepository;

    @GetMapping("")
    public ResponseEntity<?> get(@RequestParam Map<String,Object> params) {

        Long workflowId = Long.valueOf((String) params.get("workflowId"));

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow();

        List<WorkflowAuthority> results = repository.findByWorkflow(workflow);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String,Object> params) {
        Long workflowId = Long.valueOf((String) params.get("workflowId"));

        Workflow workflow = workflowRepository.findById(workflowId)
                                .orElseThrow();

        WorkflowAuthority createdData = WorkflowAuthority.builder()
                .authorityCode((String) params.get("authorityCode"))
                .workflow(workflow)
                .build();

        return new ResponseEntity<>(repository.save(createdData), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        WorkflowAuthority savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        Long workflowId = Long.valueOf((String) params.get("workflowId"));

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow();

        savedData.update(
                (String) params.get("authorityCode"),
                workflow
        );

        repository.save(savedData);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        WorkflowAuthority savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.deleteById(savedData.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
