package com.datajoy.admin_builder.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    CodeService codeService;


    @GetMapping("")
    public ResponseEntity<?> getCommonCode(@RequestBody List<CodeRequest> params) {
        Map<String, List<CodeResponse>> response = codeService.getCode(params);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
