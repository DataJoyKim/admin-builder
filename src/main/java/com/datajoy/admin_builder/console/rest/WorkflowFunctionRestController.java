package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.console.dto.WorkflowFunctionResponse;
import com.datajoy.admin_builder.message.MessageProcessor;
import com.datajoy.admin_builder.message.MessageProcessorRepository;
import com.datajoy.admin_builder.entity.Entity;
import com.datajoy.admin_builder.entity.EntityRepository;
import com.datajoy.admin_builder.function.WorkflowFunction;
import com.datajoy.admin_builder.function.WorkflowFunctionRepository;
import com.datajoy.admin_builder.function.code.FunctionType;
import com.datajoy.admin_builder.notification.Notification;
import com.datajoy.admin_builder.notification.NotificationRepository;
import com.datajoy.admin_builder.query.Query;
import com.datajoy.admin_builder.query.QueryRepository;
import com.datajoy.admin_builder.restclient.RestClient;
import com.datajoy.admin_builder.restclient.RestClientRepository;
import com.datajoy.admin_builder.util.DataTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController("console.WorkflowFunctionRestController")
@RequestMapping("/console/api/workflow-function")
public class WorkflowFunctionRestController {
    private static final String CONDITION_DISPLAY_NAME = "조건분기";

    @Autowired
    private WorkflowFunctionRepository repository;
    @Autowired
    private QueryRepository queryRepository;
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private RestClientRepository restClientRepository;
    @Autowired
    private MessageProcessorRepository messageProcessorRepository;
    @Autowired
    private NotificationRepository notificationRepository;

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
                // 흐름이 nodeId 로만 이어지므로 비어있는 채로 저장되면 안 된다.
                .nodeId(resolveNodeId((String) params.get("nodeId")))
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
                resolveNodeId((String) params.get("nodeId")),
                (String) params.get("functionName"),
                FunctionType.valueOf((String) params.get("functionType")),
                (Integer) params.get("orderNum"),
                (Boolean) params.get("isLogging"),
                (String) params.get("requestMessageId"),
                (String) params.get("responseMessageId")
        );
    }

    private static String resolveNodeId(String nodeId) {
        return (nodeId == null || nodeId.isBlank()) ? "node-" + UUID.randomUUID() : nodeId;
    }

    @GetMapping("")
    public ResponseEntity<?> get(@RequestParam Map<String,Object> params) {
        Long workflowId = Long.valueOf((String) params.get("workflowId"));
        List<WorkflowFunction> results = repository.findByWorkflowIdOrderByOrderNum(workflowId);

        List<WorkflowFunctionResponse> response = new ArrayList<>();
        for(WorkflowFunction w : results) {
            String displayName = createDisplayName(w);

            response.add(WorkflowFunctionResponse.builder()
                    .id(w.getId())
                    .workflowId(w.getWorkflowId())
                    .nodeId(w.getNodeId())
                    .functionName(w.getFunctionName())
                    .displayName(displayName)
                    .functionType(w.getFunctionType())
                    .errorResolveType(w.getErrorResolveType())
                    .orderNum(w.getOrderNum())
                    .isLogging(w.getIsLogging())
                    .requestMessageId(w.getRequestMessageId())
                    .responseMessageId(w.getResponseMessageId())
                    .build());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String createDisplayName(WorkflowFunction w) {
        String displayName = "(기능생성필요)";
        if(FunctionType.SQL.equals(w.getFunctionType())) {
            Optional<Query> func = queryRepository.findByQueryName(w.getFunctionName());
            if(func.isPresent()){
                displayName = func.get().getDisplayName();
            }
        }
        else if(FunctionType.ENTITY.equals(w.getFunctionType())) {
            Optional<Entity> func = entityRepository.findByEntityName(w.getFunctionName());
            if(func.isPresent()){
                displayName = func.get().getDisplayName();
            }
        }
        else if(FunctionType.REST_CLIENT.equals(w.getFunctionType())) {
            Optional<RestClient> func = restClientRepository.findByClientName(w.getFunctionName());
            if(func.isPresent()){
                displayName = func.get().getDisplayName();
            }
        }
        else if(FunctionType.MESSAGE_PROCESSOR.equals(w.getFunctionType())) {
            Optional<MessageProcessor> func = messageProcessorRepository.findByProcessorName(w.getFunctionName());
            if(func.isPresent()){
                displayName = func.get().getDisplayName();
            }
        }
        else if(FunctionType.NOTIFICATION.equals(w.getFunctionType())) {
            Optional<Notification> func = notificationRepository.findByNotificationName(w.getFunctionName());
            if(func.isPresent()){
                displayName = func.get().getDisplayName();
            }
        }
        else if(FunctionType.CONDITION.equals(w.getFunctionType())) {
            // 조건분기는 따로 등록해둔 기능이 없고 판정식 자체가 내용이라 고정 이름을 쓴다.
            displayName = CONDITION_DISPLAY_NAME;
        }
        return displayName;
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
