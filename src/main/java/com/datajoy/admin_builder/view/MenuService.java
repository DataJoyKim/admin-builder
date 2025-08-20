package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.domain.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public List<Menu> getMenuTree() {
        return menuRepository.findAllTree();
    }
}
