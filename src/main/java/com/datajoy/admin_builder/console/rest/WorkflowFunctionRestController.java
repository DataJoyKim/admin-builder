package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.function.WorkflowFunction;
import com.datajoy.admin_builder.function.WorkflowFunctionRepository;
import com.datajoy.admin_builder.function.code.FunctionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/workflow-function")
public class WorkflowFunctionRestController {
    @Autowired
    private WorkflowFunctionRepository repository;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String,Object> params) {
        WorkflowFunction workflowFunction = WorkflowFunction.builder()
                .workflowId(Long.valueOf((String) params.get("workflowId")))
                .functionName((String) params.get("functionName"))
                .functionType(FunctionType.valueOf((String) params.get("functionType")))
                .orderNum((Integer) params.get("orderNum"))
                .isLogging((Boolean) params.get("isLogging"))
                .requestMessageId((String) params.get("requestMessageId"))
                .responseMessageId((String) params.get("responseMessageId"))
                .build();

        return new ResponseEntity<>(repository.save(workflowFunction), HttpStatus.OK);
    }

    @GetMapping("/{workflowId}")
    public ResponseEntity<?> get(@PathVariable("workflowId") Long workflowId) {
        List<WorkflowFunction> results = repository.findByWorkflowIdOrderByOrderNum(workflowId);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        WorkflowFunction workflowFunction = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        workflowFunction.update(
                Long.valueOf((String) params.get("workflowId")),
                (String) params.get("functionName"),
                FunctionType.valueOf((String) params.get("functionType")),
                (Integer) params.get("orderNum"),
                (Boolean) params.get("isLogging"),
                (String) params.get("requestMessageId"),
                (String) params.get("responseMessageId")
        );

        return new ResponseEntity<>(repository.save(workflowFunction), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        WorkflowFunction workflowFunction = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.deleteById(workflowFunction.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
