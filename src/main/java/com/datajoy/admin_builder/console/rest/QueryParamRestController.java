
package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.console.repository.ConsoleQueryParamRepository;
import com.datajoy.admin_builder.query.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("console.QueryParamRestController")
@RequestMapping("/console/api/query-param")
public class QueryParamRestController {
    @Autowired
    private ConsoleQueryParamRepository queryParamRepository;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQueryParam(@PathVariable("id") Long id) {
        QueryParam queryParam = queryParamRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        queryParamRepository.deleteById(queryParam.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
