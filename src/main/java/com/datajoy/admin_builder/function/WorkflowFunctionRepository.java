package com.datajoy.admin_builder.function;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowFunctionRepository extends JpaRepository<WorkflowFunction, Long> {
    List<WorkflowFunction> findByWorkflowIdOrderByOrderNum(Long workflowId);
}
