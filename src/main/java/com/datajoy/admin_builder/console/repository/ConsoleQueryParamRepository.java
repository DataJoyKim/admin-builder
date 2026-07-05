package com.datajoy.admin_builder.console.repository;

import com.datajoy.admin_builder.query.QueryParam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsoleQueryParamRepository extends JpaRepository<QueryParam, Long> {
}
