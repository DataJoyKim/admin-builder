package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.domain.ViewAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViewActionRepository extends JpaRepository<ViewAction, Long> {
    List<ViewAction> findByObjectCode(String objectCode);
}
