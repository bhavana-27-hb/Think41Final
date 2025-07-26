package com.example.taskscheduler.service;

import com.example.taskscheduler.entity.Task;
import com.example.taskscheduler.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    public Task addTask(Task task) {
        task.setSubmittedAt(LocalDateTime.now());
        task.setStatus("pending");
        return repo.save(task);
    }

    public Optional<Task> getTask(String id) {
        return repo.findById(id);
    }

    public Optional<Task> updateStatus(String id, String newStatus) {
        Optional<Task> opt = repo.findById(id);
        if (opt.isPresent()) {
            Task task = opt.get();
            if ("completed".equals(task.getStatus()) && !"completed".equals(newStatus))
                throw new RuntimeException("Cannot change completed task status");
            if ("processing".equals(task.getStatus()) && "pending".equals(newStatus))
                throw new RuntimeException("Cannot regress from processing to pending");
            task.setStatus(newStatus);
            return Optional.of(repo.save(task));
        }
        return Optional.empty();
    }

    public Optional<Task> getNextTaskToProcess() {
        return repo.findNextTask();
    }

    public List<Task> getPendingTasks(String order, int limit) {
        List<Task> list = repo.findByStatusOrderByEstimatedTimeMinutesAscSubmittedAtAsc();
        if ("desc".equalsIgnoreCase(order)) Collections.reverse(list);
        return list.stream().limit(limit).toList();
    }
}
