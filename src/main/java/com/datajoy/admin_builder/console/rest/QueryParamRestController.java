
package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.query.QueryParam;
import com.datajoy.admin_builder.query.code.AutoValueType;
import com.datajoy.admin_builder.query.code.InOut;
import com.datajoy.admin_builder.query.code.ParamType;
import com.datajoy.admin_builder.console.repository.ConsoleQueryParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/query-param")
public class QueryParamRestController {
    @Autowired
    private ConsoleQueryParamRepository queryParamRepository;

    @PostMapping("")
    public ResponseEntity<?> createQueryParam(@RequestBody Map<String,Object> params) {
        queryParamRepository.insert(
                Long.valueOf((String) params.get("queryId")),
                (String) params.get("paramName"),
                ParamType.valueOf((String) params.get("paramType")),
                AutoValueType.valueOf((String) params.get("autoValueType")),
                InOut.valueOf((String) params.get("inOut"))
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/{queryId}")
    public ResponseEntity<?> getQueryParam(@PathVariable("queryId") Long queryId) {
        List<QueryParam> results = queryParamRepository.findByQueryId(queryId);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQueryParam(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        QueryParam queryParam = queryParamRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        queryParamRepository.update(
                queryParam.getId(),
                (String) params.get("paramName"),
                ParamType.valueOf((String) params.get("paramType")),
                AutoValueType.valueOf((String) params.get("autoValueType")),
                InOut.valueOf((String) params.get("inOut"))
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQueryParam(@PathVariable("id") Long id) {
        QueryParam queryParam = queryParamRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        queryParamRepository.deleteById(queryParam.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
