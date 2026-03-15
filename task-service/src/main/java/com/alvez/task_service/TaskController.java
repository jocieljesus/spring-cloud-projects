package com.alvez.task_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.task_service.tasks.api.TasksApi;
import com.fiap.task_service.tasks.dto.CreateTaskRequestDTO;
import com.fiap.task_service.tasks.dto.PaginatedResultDTO;
import com.fiap.task_service.tasks.dto.TaskDTO;
import com.fiap.task_service.tasks.dto.UpdateTaskRequestDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TaskController implements TasksApi {
    
    @Autowired
    private final TaskService taskService;

    public ResponseEntity<Void> cancelTask(final Long taskId) {
        taskService.cancelTask(taskId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> completeTaskEntity(final Long taskId) {
        taskService.completeTask(taskId);
        return ResponseEntity.noContent().build();
    }
    public ResponseEntity<TaskDTO> createTask(@Valid final CreateTaskRequestDTO body) {
        final var created = taskService.createTask(body);
        return ResponseEntity.ok(created);
    }
    
    public ResponseEntity<Void> executeTask(final Long taskId) {
        taskService.executeTask(taskId);
        return ResponseEntity.noContent().build();
    }

    
    public ResponseEntity<PaginatedResultDTO> getAllTasks(final Integer page, final Integer size, final String sort, final String status) {
        final var result = taskService.getAllTasks(page, size, sort, status);
        return ResponseEntity.ok(result);
    }

     public ResponseEntity<TaskDTO> getTask(final Long taskId) {
        return ResponseEntity.ok(taskService.getTask(taskId));
     }

     public ResponseEntity<TaskDTO> updateTask(final Long taskId, @Valid final UpdateTaskRequestDTO body) {
        final var updated = taskService.updateTask(taskId, body);
        return ResponseEntity.ok(updated);
     }
    

}
