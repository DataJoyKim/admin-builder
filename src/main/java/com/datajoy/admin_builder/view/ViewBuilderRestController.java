package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.domain.Menu;
import com.datajoy.admin_builder.view.dto.MenuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ViewBuilderRestController {
    @Autowired
    MenuService menuService;
    @GetMapping("/api/{appNm}/menu")
    public ResponseEntity<?> getMenu(@PathVariable(name = "appNm") String appNm) {
        List<MenuDto> menuList = menuService.getMenuTree();

        return new ResponseEntity<>(menuList, HttpStatus.OK);
    }
}
