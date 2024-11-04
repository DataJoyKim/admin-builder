package com.datajoy.web_builder.console.rest;

import com.datajoy.web_builder.apibuilder.entity.EntityColumn;
import com.datajoy.web_builder.apibuilder.entity.code.*;
import com.datajoy.web_builder.console.repository.ConsoleEntityColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/entity-column")
public class EntityColumnRestController {
    @Autowired
    private ConsoleEntityColumnRepository entityColumnRepository;

    @PostMapping("")
    public ResponseEntity<?> createEntityColumn(@RequestBody Map<String,Object> params) {
        entityColumnRepository.insert(
                Long.valueOf((String) params.get("entityId")),
                (String) params.get("columnName"),
                (String) params.get("displayName"),
                Boolean.valueOf((String) params.get("useKey")),
                ColumnType.valueOf((String) params.get("columnType"))
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/{entityId}")
    public ResponseEntity<?> getEntityColumn(@PathVariable("entityId") Long entityId) {
        List<EntityColumn> results = entityColumnRepository.findByEntityId(entityId);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntityColumn(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        EntityColumn entityColumn = entityColumnRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        entityColumnRepository.update(
                entityColumn.getId(),
                Long.valueOf((String) params.get("entityId")),
                (String) params.get("columnName"),
                (String) params.get("displayName"),
                Boolean.valueOf((String) params.get("useKey")),
                ColumnType.valueOf((String) params.get("columnType")),
                SelectWhereType.valueOf((String) params.get("selectWhereType")),
                (String) params.get("selectWhereCompareOperator"),
                Integer.valueOf((String) params.get("selectOrderByNum")),
                SortOrder.valueOf((String) params.get("selectOrderBySortOrder")),
                AutoValueType.valueOf((String) params.get("insertAutoValueType")),
                (String) params.get("insertAutoValue"),
                NullResolveType.valueOf((String) params.get("insertNullResolveType")),
                AutoValueType.valueOf((String) params.get("updateAutoValueType")),
                (String) params.get("updateAutoValue"),
                NullResolveType.valueOf((String) params.get("updateNullResolveType")),
                AutoValueType.valueOf((String) params.get("deleteAutoValueType")),
                (String) params.get("deleteAutoValue")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntityColumn(@PathVariable("id") Long id) {
        EntityColumn entityColumn = entityColumnRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        entityColumnRepository.deleteById(entityColumn.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
