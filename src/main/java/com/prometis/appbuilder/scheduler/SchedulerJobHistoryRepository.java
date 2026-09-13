package com.prometis.appbuilder.scheduler;

import com.prometis.appbuilder.scheduler.domain.SchedulerJobHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchedulerJobHistoryRepository extends JpaRepository<SchedulerJobHistory, Long> {
    List<SchedulerJobHistory> findTop50BySchedulerJobIdOrderByStartedAtDesc(Long schedulerJobId);

    Optional<SchedulerJobHistory> findFirstBySchedulerJobIdOrderByStartedAtDesc(Long schedulerJobId);

    void deleteBySchedulerJobId(Long schedulerJobId);
}
