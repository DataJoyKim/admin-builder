package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.security.AuthenticatedUser;
import com.datajoy.admin_builder.security.GrantedAuthority;
import com.datajoy.admin_builder.view.domain.Menu;
import com.datajoy.admin_builder.view.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuAuthorityRepository menuAuthorityRepository;

    public List<MenuDto> getMenuTree() {
        List<Menu> menuList = menuRepository.findAllTree();

        menuList.forEach(Menu::processing);

        return MenuDto.of(menuList);
    }

    public List<MenuDto> getMenuTree(String parentMenuCd) {
        Menu parentMenu = menuRepository.findByMenuCd(parentMenuCd).orElseThrow();

        List<Menu> menuList = menuRepository.findAllTree(parentMenu);

        menuList.forEach(Menu::processing);

        return MenuDto.of(menuList);
    }

    public List<MenuDto> getMenuRoot(AuthenticatedUser user) {
        List<GrantedAuthority> grantedAuthorities = user.getGrantedAuthorities();
        if(grantedAuthorities == null || grantedAuthorities.isEmpty()) {
            return new ArrayList<>();
        }

        List<Menu> menuList = new ArrayList<>();
        for(GrantedAuthority grantedAuthority : grantedAuthorities) {
            List<MenuAuthority> menuAuthorities = menuAuthorityRepository.findByAuthorityCode(grantedAuthority.getRole());

            for(MenuAuthority menuAuthority : menuAuthorities) {
                menuList.add(menuAuthority.getMenu());
            }
        }

        menuList.forEach(Menu::processing);

        return MenuDto.of(menuList);
    }
}
