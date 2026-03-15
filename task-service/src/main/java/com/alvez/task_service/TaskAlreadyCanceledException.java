package com.alvez.task_service;

public class TaskAlreadyCanceledException extends RuntimeException {
    public TaskAlreadyCanceledException(final Long id) {
        super("Task with id " + id + " is already canceled.");
    }
    
}
