package com.example.taskscheduler.controller;

import com.example.taskscheduler.entity.Task;
import com.example.taskscheduler.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task) {
        if (task.getEstimatedTimeMinutes() <= 0)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.addTask(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> get(@PathVariable String id) {
        return service.getTask(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            String newStatus = body.get("new_status");
            return service.updateStatus(id, newStatus).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/next-to-process")
    public ResponseEntity<?> getNext() {
        return service.getNextTaskToProcess().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Task>> list(@RequestParam(defaultValue = "asc") String order,
                                           @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(service.getPendingTasks(order, limit));
    }
}
