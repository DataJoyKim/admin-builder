package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuAuthorityRepository extends JpaRepository<MenuAuthority, Long> {
    List<MenuAuthority> findByMenu(Menu menu);

    List<MenuAuthority> findByAuthorityCode(String authorityCode);
}
