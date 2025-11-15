package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.function.WorkflowFunction;
import com.datajoy.admin_builder.function.WorkflowFunctionRepository;
import com.datajoy.admin_builder.function.code.FunctionType;
import com.datajoy.admin_builder.util.DataTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController("console.WorkflowFunctionRestController")
@RequestMapping("/console/api/workflow-function")
public class WorkflowFunctionRestController {
    @Autowired
    private WorkflowFunctionRepository repository;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody List<Map<String,Object>> params) {

        for(Map<String,Object> param : params) {
            WorkflowFunction workflowFunction;
            Object idObj = param.get("id");
            if(idObj == null) {
                workflowFunction = createWorkflowFunction(param);
            }
            else {
                workflowFunction = repository.findById(DataTypeUtil.valueLongOf(idObj))
                        .orElseThrow(RuntimeException::new);

                updateWorkflowFunction(param, workflowFunction);
            }

            repository.save(workflowFunction);
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String,Object> params) {
        WorkflowFunction workflowFunction = createWorkflowFunction(params);

        return new ResponseEntity<>(repository.save(workflowFunction), HttpStatus.OK);
    }

    private static WorkflowFunction createWorkflowFunction(Map<String, Object> params) {
        return WorkflowFunction.builder()
                .workflowId(DataTypeUtil.valueLongOf(params.get("workflowId")))
                .functionName((String) params.get("functionName"))
                .functionType(FunctionType.valueOf((String) params.get("functionType")))
                .orderNum((Integer) params.get("orderNum"))
                .isLogging((Boolean) params.get("isLogging"))
                .requestMessageId((String) params.get("requestMessageId"))
                .responseMessageId((String) params.get("responseMessageId"))
                .build();
    }

    private static void updateWorkflowFunction(Map<String, Object> params, WorkflowFunction workflowFunction) {
        workflowFunction.update(
                DataTypeUtil.valueLongOf(params.get("workflowId")),
                (String) params.get("functionName"),
                FunctionType.valueOf((String) params.get("functionType")),
                (Integer) params.get("orderNum"),
                (Boolean) params.get("isLogging"),
                (String) params.get("requestMessageId"),
                (String) params.get("responseMessageId")
        );
    }

    @GetMapping("")
    public ResponseEntity<?> get(@RequestParam Map<String,Object> params) {
        Long workflowId = Long.valueOf((String) params.get("workflowId"));
        List<WorkflowFunction> results = repository.findByWorkflowIdOrderByOrderNum(workflowId);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        WorkflowFunction workflowFunction = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        updateWorkflowFunction(params, workflowFunction);

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
