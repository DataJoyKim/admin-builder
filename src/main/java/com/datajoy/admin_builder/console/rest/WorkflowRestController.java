package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.function.WorkflowFunction;
import com.datajoy.admin_builder.function.WorkflowFunctionRepository;
import com.datajoy.admin_builder.function.code.FunctionType;
import com.datajoy.admin_builder.workflow.Workflow;
import com.datajoy.admin_builder.workflow.WorkflowAuthority;
import com.datajoy.admin_builder.workflow.WorkflowAuthorityRepository;
import com.datajoy.admin_builder.workflow.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController("console.WorkflowRestController")
@RequestMapping("/console/api/workflow")
public class WorkflowRestController {
    @Autowired
    private WorkflowRepository repository;
    @Autowired
    private WorkflowFunctionRepository workflowFunctionRepository;
    @Autowired
    private WorkflowAuthorityRepository workflowAuthorityRepository;

    @Transactional
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Map<String,Object> params) {
        Map<String,Object> workflowParams = (Map<String, Object>) params.get("workflow");
        List<Map<String,Object>> workflowFunctionsParams = (List<Map<String,Object>>) params.get("workflowFunctions");
        List<Map<String,Object>> workflowAuthorityParams = (List<Map<String,Object>>) params.get("workflowAuthority");

        Long id = (workflowParams.get("id") == null || ((String) workflowParams.get("id")).isEmpty())
                ? null
                : Long.valueOf((String) workflowParams.get("id"));

        Workflow workflow;

        if (id == null) {
            workflow = Workflow.builder()
                    .workflowCode((String) workflowParams.get("workflowCode"))
                    .displayName((String) workflowParams.get("displayName"))
                    .note((String) workflowParams.get("note"))
                    .useAuthValidation((Boolean) workflowParams.get("useAuthValidation"))
                    .build();
        }
        else {
            workflow = repository.findById(id)
                    .orElseThrow();

            workflow.update(
                    (String) workflowParams.get("workflowCode"),
                    (String) workflowParams.get("displayName"),
                    (String) workflowParams.get("note"),
                    (Boolean) workflowParams.get("useAuthValidation")
            );
        }

        Workflow savedWorkflow = repository.save(workflow);

        workflowFunctionRepository.deleteByWorkflowId(savedWorkflow.getId());

        for(Map<String,Object> param : workflowFunctionsParams) {
            WorkflowFunction workflowFunction = WorkflowFunction.builder()
                    .workflowId(savedWorkflow.getId())
                    .functionName((String) param.get("functionName"))
                    .functionType(FunctionType.valueOf((String) param.get("functionType")))
                    .orderNum((Integer) param.get("orderNum"))
                    .isLogging((Boolean) param.get("isLogging"))
                    .requestMessageId((String) param.get("requestMessageId"))
                    .responseMessageId((String) param.get("responseMessageId"))
                    .build();

            workflowFunctionRepository.save(workflowFunction);
        }

        workflowAuthorityRepository.deleteByWorkflowId(savedWorkflow.getId());

        for(Map<String,Object> param : workflowAuthorityParams) {
            WorkflowAuthority workflowAuthority = WorkflowAuthority.builder()
                    .authorityCode((String) param.get("authorityCode"))
                    .workflow(savedWorkflow)
                    .build();

            workflowAuthorityRepository.save(workflowAuthority);
        }

        return ResponseEntity.ok(savedWorkflow);
    }
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Workflow workflow = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        workflowFunctionRepository.deleteByWorkflowId(workflow.getId());
        workflowAuthorityRepository.deleteByWorkflowId(workflow.getId());
        repository.deleteById(workflow.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

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
                .workflowCode((String) params.get("workflowCode"))
                .displayName((String) params.get("displayName"))
                .note((String) params.get("note"))
                .useAuthValidation((Boolean) params.get("useAuthValidation"))
                .build();

        Workflow results = repository.save(workflowBuilder);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        Workflow workflowBuilder = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        workflowBuilder.update(
                (String) params.get("workflowCode"),
                (String) params.get("displayName"),
                (String) params.get("note"),
                (Boolean) params.get("useAuthValidation")
        );

        Workflow results = repository.save(workflowBuilder);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
