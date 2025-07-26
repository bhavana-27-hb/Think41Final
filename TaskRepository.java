package com.example.taskscheduler.repository;

import com.example.taskscheduler.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {

    @Query(value = "SELECT * FROM TASK WHERE status = 'pending' ORDER BY estimated_time_minutes ASC, submitted_at ASC LIMIT 1", nativeQuery = true)
    Optional<Task> findNextTask();

    List<Task> findByStatusOrderByEstimatedTimeMinutesAscSubmittedAtAsc();
}
