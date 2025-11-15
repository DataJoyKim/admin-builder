package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.customcode.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController("console.CustomCodeRestController")
@RequestMapping("/console/api/custom-code")
public class CustomCodeRestController {
    @Autowired
    private CustomCodeRepository repository;
    @Autowired
    private CustomCodeService customCodeService;

    @GetMapping("")
    public ResponseEntity<?> get(@RequestParam(name = "codeName") String codeName) {
        Optional<CustomCode> results = repository.findByCodeName(codeName);
        if(results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(results.get(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Map<String,Object> params) {
        Object idObj = params.get("id");

        CustomCode data;
        if(idObj == null) {
            data = CustomCode.builder()
                    .codeName((String) params.get("codeName"))
                    .displayName((String) params.get("displayName"))
                    .script((String) params.get("script"))
                    .build();
        }
        else {
            data = repository.findById(Long.valueOf((String) idObj)).orElseThrow();

            data.update(
                (String) params.get("displayName"),
                (String) params.get("script")
            );
        }

        CustomCode results = repository.save(data);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        CustomCode query = repository.findById(id)
                .orElseThrow();

        repository.deleteById(query.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{codeName}/execute")
    public ResponseEntity<?> execute(
            @PathVariable("codeName") String codeName,
            @RequestBody List<Map<String, Object>> params
    ) {
        CustomCodeRequest request = CustomCodeRequest.builder()
                .contents(params)
                .build();

        CustomCodeResult results = customCodeService.execute(codeName, request);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
