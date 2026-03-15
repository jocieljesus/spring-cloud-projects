package com.alvez.task_service;

public class TaskAlreadyInProgressException extends RuntimeException {
    public TaskAlreadyInProgressException(final Long id) {
        super("Task with id " + id + " is already in progress.");
    }
    
}
