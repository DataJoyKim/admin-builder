package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.apibuilder.service.FunctionType;
import com.datajoy.admin_builder.apibuilder.service.ServiceFunction;
import com.datajoy.admin_builder.console.repository.ConsoleServiceFunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/service-function")
public class ServiceFunctionRestController {
    @Autowired
    private ConsoleServiceFunctionRepository serviceFunctionRepository;

    @PostMapping("")
    public ResponseEntity<?> createServiceFunction(@RequestBody Map<String,Object> params) {
        serviceFunctionRepository.insert(
                Long.valueOf((String) params.get("serviceId")),
                (String) params.get("functionName"),
                FunctionType.valueOf((String) params.get("functionType")),
                (Integer) params.get("orderNum"),
                (Boolean) params.get("isLogging"),
                (String) params.get("requestMessageId"),
                (String) params.get("responseMessageId")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<?> getServiceFunction(@PathVariable("serviceId") Long serviceId) {
        List<ServiceFunction> results = serviceFunctionRepository.findByServiceIdOrderByOrderNum(serviceId);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateServiceFunction(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        ServiceFunction serviceFunction = serviceFunctionRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        serviceFunctionRepository.update(
                serviceFunction.getId(),
                Long.valueOf((String) params.get("serviceId")),
                (String) params.get("functionName"),
                FunctionType.valueOf((String) params.get("functionType")),
                (Integer) params.get("orderNum"),
                (Boolean) params.get("isLogging"),
                (String) params.get("requestMessageId"),
                (String) params.get("responseMessageId")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteServiceFunction(@PathVariable("id") Long id) {
        ServiceFunction serviceFunction = serviceFunctionRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        serviceFunctionRepository.deleteById(serviceFunction.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
