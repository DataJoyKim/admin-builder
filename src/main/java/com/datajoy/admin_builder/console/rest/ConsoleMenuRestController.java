package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.view.MenuRepository;
import com.datajoy.admin_builder.view.ViewObjectRepository;
import com.datajoy.admin_builder.view.domain.Menu;
import com.datajoy.admin_builder.view.domain.ViewObject;
import com.datajoy.admin_builder.view.dto.MenuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/console/api/menu")
public class ConsoleMenuRestController {
    @Autowired
    private MenuRepository repository;
    @Autowired
    private ViewObjectRepository viewObjectRepository;

    @GetMapping("/tree")
    public ResponseEntity<?> getList() {
        List<MenuDto> results = MenuDto.of(repository.findAllTree());

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Menu results = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String,Object> params) {
        String parentMenuCd = (String) params.get("parentMenuCd");

        Optional<Menu> parentMenuOptional = repository.findByMenuCd(parentMenuCd);
        Menu parentMenu = null;
        if(parentMenuOptional.isPresent()) {
            parentMenu = parentMenuOptional.get();
        }

        String objectCd = (String) params.get("objectCd");

        ViewObject viewObject = null;
        Optional<ViewObject> viewObjectOptional = viewObjectRepository.findByObjectCd(objectCd);
        if(viewObjectOptional.isPresent()) {
            viewObject = viewObjectOptional.get();
        }

        Menu createdData = Menu.builder()
                .menuCd((String) params.get("menuCd"))
                .menuNm((String) params.get("menuNm"))
                .orderNum(Integer.valueOf((String) params.get("orderNum")))
                .parentMenu(parentMenu)
                .viewObject(viewObject)
                .build();

        return new ResponseEntity<>(repository.save(createdData), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        String parentMenuCd = (String) params.get("parentMenuCd");

        Optional<Menu> parentMenuOptional = repository.findByMenuCd(parentMenuCd);
        Menu parentMenu = null;
        if(parentMenuOptional.isPresent()) {
            parentMenu = parentMenuOptional.get();
        }

        String objectCd = (String) params.get("objectCd");

        ViewObject viewObject = null;
        Optional<ViewObject> viewObjectOptional = viewObjectRepository.findByObjectCd(objectCd);
        if(viewObjectOptional.isPresent()) {
            viewObject = viewObjectOptional.get();
        }

        Menu savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        savedData.update(
                (String) params.get("menuCd"),
                (String) params.get("menuNm"),
                Integer.valueOf((String) params.get("orderNum")),
                parentMenu,
                viewObject
        );

        repository.save(savedData);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Menu savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.deleteById(savedData.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
