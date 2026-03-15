package com.alvez.task_service;

public class TaskAlreadyCompletedException extends RuntimeException {
    public TaskAlreadyCompletedException(final Long id) {
        super("Task with id " + id + " is already completed.");
    }
    
}
