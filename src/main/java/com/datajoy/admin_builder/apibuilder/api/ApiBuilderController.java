package com.datajoy.admin_builder.apibuilder.api;

import com.datajoy.admin_builder.apibuilder.service.ServiceBuilderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ApiBuilderController {
    @Autowired
    ServiceBuilderService serviceBuilderService;

    @RequestMapping(value = "/{applicationName}/api/**", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
    public ResponseEntity<?> callRestAPI(
            HttpServletRequest request,
            @PathVariable String applicationName,
            @RequestParam Map<String, Object> requestParams,
            @RequestBody Object requestBody
    ) {
        String path = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        //ResponseMessage<?> response = apiService.build(applicationName, path, method, requestParams, requestBody);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
