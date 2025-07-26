package com.example.taskscheduler.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    private String taskStrId;

    private String description;

    private int estimatedTimeMinutes;

    private String status; // pending, processing, completed

    private LocalDateTime submittedAt;
}
