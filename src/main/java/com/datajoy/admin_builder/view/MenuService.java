package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.domain.Menu;
import com.datajoy.admin_builder.view.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public List<MenuDto> getMenuTree() {
        return MenuDto.of(menuRepository.findAllTree());
    }

    public List<MenuDto> getMenuTree(String parentMenuCd) {
        Menu parentMenu = menuRepository.findByMenuCd(parentMenuCd).orElseThrow();

        return MenuDto.of(menuRepository.findAllTree(parentMenu));
    }

    public List<MenuDto> getMenuRoot() {
        List<Menu> menuList = menuRepository.findByParentMenu(null);

        return MenuDto.of(menuList);
    }
}
