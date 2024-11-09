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
                (Boolean) params.get("useKey"),
                ColumnType.valueOf((String) params.get("columnType"))
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/{entityId}")
    public ResponseEntity<?> getEntityColumn(@PathVariable("entityId") Long entityId) {
        List<EntityColumn> results = entityColumnRepository.findByEntityIdOrderByOrderNum(entityId);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntityColumn(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        EntityColumn entityColumn = entityColumnRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        entityColumnRepository.update(
                entityColumn.getId(),
                (String) params.get("columnName"),
                (String) params.get("displayName"),
                (Boolean) params.get("useKey"),
                ColumnType.valueOf((String) params.get("columnType")),
                (Integer) params.get("orderNum")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PutMapping("/{id}/select")
    public ResponseEntity<?> updateEntityColumnSelectSetting(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        EntityColumn entityColumn = entityColumnRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        entityColumnRepository.updateSelectSetting(
                entityColumn.getId(),
                SelectWhereType.valueOf((String) params.get("selectWhereType")),
                (String) params.get("selectWhereCompareOperator"),
                (Integer) params.get("selectOrderByNum"),
                SortOrder.valueOf((String) params.get("selectOrderBySortOrder"))
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PutMapping("/{id}/insert")
    public ResponseEntity<?> updateEntityColumnInsertSetting(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        EntityColumn entityColumn = entityColumnRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        entityColumnRepository.updateInsertSetting(
                entityColumn.getId(),
                AutoValueType.valueOf((String) params.get("insertAutoValueType")),
                (String) params.get("insertAutoValue"),
                NullResolveType.valueOf((String) params.get("insertNullResolveType"))
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateEntityColumnUpdateSetting(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        EntityColumn entityColumn = entityColumnRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        entityColumnRepository.updateUpdateSetting(
                entityColumn.getId(),
                AutoValueType.valueOf((String) params.get("updateAutoValueType")),
                (String) params.get("updateAutoValue"),
                NullResolveType.valueOf((String) params.get("updateNullResolveType"))
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<?> updateEntityColumnDeleteSetting(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        EntityColumn entityColumn = entityColumnRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        entityColumnRepository.updateDeleteSetting(
                entityColumn.getId(),
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
