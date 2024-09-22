package com.datajoy.web_builder.apibuilder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiBuilderController {

    @RequestMapping(value = "/{applicationName}/**", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
    public ResponseEntity<?> callRestAPI(
            @PathVariable String applicationName
    ) {
        System.out.println("callRestAPI");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
