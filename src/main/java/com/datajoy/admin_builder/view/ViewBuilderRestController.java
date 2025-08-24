package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.dto.MenuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ViewBuilderRestController {
    @Autowired
    MenuService menuService;
    @GetMapping("/api/menu/tree")
    public ResponseEntity<?> getMenu(@RequestParam("parentMenuCd") String parentMenuCd) {
        List<MenuDto> menuList = menuService.getMenuTree(parentMenuCd);

        return new ResponseEntity<>(menuList, HttpStatus.OK);
    }
    @GetMapping("/api/menu/root")
    public ResponseEntity<?> getMenuRoot() {
        List<MenuDto> menuList = menuService.getMenuRoot();

        return new ResponseEntity<>(menuList, HttpStatus.OK);
    }
}
