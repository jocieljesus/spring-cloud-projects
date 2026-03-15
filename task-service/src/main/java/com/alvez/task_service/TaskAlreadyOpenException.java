package com.alvez.task_service;

public class TaskAlreadyOpenException extends RuntimeException {
    public TaskAlreadyOpenException(final Long id) {
        super("Task with id " + id + " is already open.");
    }
    
}
