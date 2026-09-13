package com.prometis.appbuilder.view;

import com.prometis.appbuilder.view.domain.ViewCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViewCodeRepository extends JpaRepository<ViewCode, Long> {
    List<ViewCode> findByObjectCode(String objectCode);
}
