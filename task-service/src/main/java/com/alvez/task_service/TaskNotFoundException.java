package com.alvez.task_service;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(final Long taskId) {
        super("Task with id " + taskId + " not found");
    }

}
