package com.datajoy.admin_builder.apibuilder.service;

import com.datajoy.admin_builder.apibuilder.message.RequestMessage;
import com.datajoy.admin_builder.apibuilder.message.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ServiceBuilderController {
    @Autowired
    ServiceBuilderService serviceBuilderService;

    @PostMapping("/service/{serviceName}")
    public ResponseEntity<?> service(
            @PathVariable("serviceName") String serviceName,
            @RequestBody RequestMessage requestMessage
    ) {
        ResponseMessage response = serviceBuilderService.execute(serviceName, requestMessage);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
