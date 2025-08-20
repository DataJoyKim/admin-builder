package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long> {
    @Query("select m from Menu m left join fetch m.children where m.parentMenu is null")
    List<Menu> findAllTree();
}
