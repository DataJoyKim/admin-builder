package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.view.MenuAuthority;
import com.datajoy.admin_builder.view.MenuAuthorityRepository;
import com.datajoy.admin_builder.view.MenuRepository;
import com.datajoy.admin_builder.view.domain.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/menu-authority")
public class ConsoleMenuAuthorityRestController {
    @Autowired
    private MenuAuthorityRepository repository;
    @Autowired
    private MenuRepository menuRepository;

    @GetMapping("")
    public ResponseEntity<?> get(@RequestParam Map<String,Object> params) {

        String authorityCode = (String) params.get("authorityCode");

        List<MenuAuthority> results = repository.findByAuthorityCode(authorityCode);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String,Object> params) {
        String menuCd = (String) params.get("menuCd");

        Menu menu = menuRepository.findByMenuCd(menuCd)
                .orElseThrow();

        MenuAuthority createdData = MenuAuthority.builder()
                .authorityCode((String) params.get("authorityCode"))
                .menu(menu)
                .build();

        repository.save(createdData);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        MenuAuthority savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        String menuCd = (String) params.get("menuCd");

        Menu menu = menuRepository.findByMenuCd(menuCd)
                .orElseThrow();

        savedData.update(
                (String) params.get("authorityCode"),
                menu
        );

        repository.save(savedData);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        MenuAuthority savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.deleteById(savedData.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
