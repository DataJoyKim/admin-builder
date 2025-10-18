package com.datajoy.admin_builder.workflow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    Optional<Workflow> findByWorkflowCode(String workflowCode);
}
