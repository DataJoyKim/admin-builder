package com.datajoy.web_builder.console;

import com.datajoy.web_builder.apibuilder.ApiBuilder;
import com.datajoy.web_builder.apibuilder.ApiBuilderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/console/{applicationName}/**")
public class ConsoleController {
    @Autowired
    private ApiBuilderRepository apiBuilderRepository;

    @GetMapping("/api-builder/{apiBuilderId}")
    public ResponseEntity<?> getApiBuilder(
            @PathVariable String applicationName,
            @PathVariable Long apiBuilderId
    ) {
        ApiBuilder apiBuilder = apiBuilderRepository.findBy(applicationName, apiBuilderId);

        return new ResponseEntity<>(apiBuilder, HttpStatus.OK);
    }

    @GetMapping("/api-builder")
    public ResponseEntity<?> getApiBuilders(
            @PathVariable String applicationName
    ) {
        List<ApiBuilder> apiBuilders = apiBuilderRepository.findBy(applicationName);

        return new ResponseEntity<>(apiBuilders, HttpStatus.OK);
    }
}
