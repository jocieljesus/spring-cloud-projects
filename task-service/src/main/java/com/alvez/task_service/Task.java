package com.alvez.task_service;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {
     
    enum Status {
        OPEN,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    private Long id;

    private String title;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = true)
    private Instant updatedAt;


    @Column(name = "deleted_at", nullable = true, updatable = true)
    private Instant deletedAt;

    private Task(final String title, final String description, final Status status) {
        this.title = 
        this.description = description;
        this.status = status;
    }

    @SuppressWarnings("unused")
    static Task newTask(final String title, final String description) {
        return new Task(title, description, Status.OPEN);
    }

    public void cancel() {
        if (this.status == Status.CANCELLED) {
            throw new TaskAlreadyCanceledException(this.getId());
        }
        if( this.status == Status.COMPLETED) {
            throw new TaskAlreadyCompletedException(this.getId());
        }
        this.setDeletedAt(Instant.now());
        this.setStatus(Status.CANCELLED);     
    }

    public void execute() {
        if( !this.status.equals(Status.OPEN)) {
            throw new TaskAlreadyOpenException(this.getId());
        }
        this.setStatus(Status.IN_PROGRESS);
    }

    public void complete() {
        if( !this.status.equals(Status.IN_PROGRESS)) {
            throw new TaskAlreadyInProgressException(this.getId());
        }
        this.setStatus(Status.COMPLETED);
    }

    public void update(final String title, final String description) {
        this.setTitle(title);
        this.setDescription(description);
    }
}
