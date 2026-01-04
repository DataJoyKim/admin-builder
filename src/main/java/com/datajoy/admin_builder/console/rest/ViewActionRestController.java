package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.view.ViewActionRepository;
import com.datajoy.admin_builder.view.code.ActionType;
import com.datajoy.admin_builder.view.domain.ViewAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController("console.ViewActionRestController")
@RequestMapping("/console/api/action")
public class ViewActionRestController {
    @Autowired
    private ViewActionRepository repository;

    @GetMapping("")
    public ResponseEntity<?> getList(@RequestParam("objectCode") String objectCode) {
        List<ViewAction> viewActions = repository.findByObjectCode(objectCode);

        return new ResponseEntity<>(viewActions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        ViewAction results = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/upsert")
    public ResponseEntity<?> upsert(@RequestBody Map<String,Object> params) {
        Object idObj = params.get("id");
        ViewAction data;
        if(idObj != null) {
            data = repository.findById(Long.valueOf((String) params.get("id")))
                                    .orElseThrow();
            data.update(
                    (String) params.get("objectCode"),
                    (String) params.get("actionName"),
                    (String) params.get("displayName"),
                    ActionType.valueOf((String) params.get("type")),
                    (String) params.get("argsName"),
                    (String) params.get("workflowCode"),
                    (String) params.get("workflowRequestMessageId"),
                    (String) params.get("workflowResponseMessageId"),
                    (String) params.get("workflowResponseGridId"),
                    (String) params.get("script")
            );
        }
        else {
            data = ViewAction.builder()
                    .objectCode((String) params.get("objectCode"))
                    .actionName((String) params.get("actionName"))
                    .displayName((String) params.get("displayName"))
                    .type(ActionType.valueOf((String) params.get("type")))
                    .argsName((String) params.get("argsName"))
                    .workflowCode((String) params.get("workflowCode"))
                    .workflowRequestMessageId((String) params.get("workflowRequestMessageId"))
                    .workflowResponseMessageId((String) params.get("workflowResponseMessageId"))
                    .workflowResponseGridId((String) params.get("workflowResponseGridId"))
                    .script((String) params.get("script"))
                    .build();
        }

        return new ResponseEntity<>(repository.save(data), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        ViewAction savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.deleteById(savedData.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
