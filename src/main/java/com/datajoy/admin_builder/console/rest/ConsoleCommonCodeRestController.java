package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.code.CommonCode;
import com.datajoy.admin_builder.code.CommonCodeKind;
import com.datajoy.admin_builder.code.CommonCodeKindRepository;
import com.datajoy.admin_builder.code.CommonCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/common-code")
public class ConsoleCommonCodeRestController {
    @Autowired
    private CommonCodeKindRepository commonCodeKindRepository;
    @Autowired
    private CommonCodeRepository repository;

    @GetMapping("")
    public ResponseEntity<?> get(@RequestParam Map<String,Object> params) {
        String codeKindCode = (String) params.get("codeKindCode");

        CommonCodeKind codeKind = commonCodeKindRepository.findByCode(codeKindCode)
                                            .orElseThrow();

        List<CommonCode> results = repository.findByCodKind(codeKind);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String,Object> params) {
        String codeKindCode = (String) params.get("codeKindCode");

        CommonCodeKind codeKind = commonCodeKindRepository.findByCode(codeKindCode)
                .orElseThrow();

        CommonCode createdData = CommonCode.builder()
                .code((String) params.get("code"))
                .name((String) params.get("name"))
                .codeKind(codeKind)
                .build();

        repository.save(createdData);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        CommonCode savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        String codeKindCode = (String) params.get("codeKindCode");

        CommonCodeKind codeKind = commonCodeKindRepository.findByCode(codeKindCode)
                .orElseThrow();

        savedData.update(
                (String) params.get("code"),
                (String) params.get("name"),
                codeKind
        );

        repository.save(savedData);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        CommonCode savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.deleteById(savedData.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
