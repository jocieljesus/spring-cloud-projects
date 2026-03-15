package com.alvez.task_service;

import com.fiap.task_service.tasks.dto.CreateTaskRequestDTO;
import com.fiap.task_service.tasks.dto.TaskDTO;
import com.fiap.task_service.tasks.dto.TaskDTO.StatusEnum;

public class TaskMapper {
    private TaskMapper(){
        throw new IllegalStateException("Utility class");
    }
    
    public static Task toEntity(final CreateTaskRequestDTO createTaskRequestDTO) {    
        return Task.builder()
            .title(createTaskRequestDTO.getTitle())
            .description(createTaskRequestDTO.getDescription())
            .build();
    }

    public static TaskDTO toDTO(final Task task) {
        return new TaskDTO()
            .id(task.getId())
            .title(task.getTitle())
            .description(task.getDescription())
            .status(StatusEnum.fromValue(task.getStatus().name()));
    }
}

