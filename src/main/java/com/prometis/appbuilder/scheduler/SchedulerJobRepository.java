package com.prometis.appbuilder.scheduler;

import com.prometis.appbuilder.scheduler.domain.SchedulerJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchedulerJobRepository extends JpaRepository<SchedulerJob, Long> {
    Optional<SchedulerJob> findByJobCode(String jobCode);

    List<SchedulerJob> findByEnabledTrue();
}
