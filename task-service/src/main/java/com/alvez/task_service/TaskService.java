package com.alvez.task_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fiap.task_service.tasks.dto.CreateTaskRequestDTO;
import com.fiap.task_service.tasks.dto.PaginatedResultDTO;
import com.fiap.task_service.tasks.dto.TaskDTO;
import com.fiap.task_service.tasks.dto.UpdateTaskRequestDTO;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class TaskService {

    @NotNull
    @Autowired
    private final TaskRepository taskRepository;

    public TaskDTO createTask(final CreateTaskRequestDTO taskDto) {
        final var toCreate = Task.newTask(
            taskDto.getTitle(),
            taskDto.getDescription());

        final var created = taskRepository.save(toCreate);
        return TaskMapper.toDTO(created);
    }


    public TaskDTO updateTask( final Long taskId, final UpdateTaskRequestDTO taskDto) {
        final var toUpdate = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        toUpdate.setTitle(taskDto.getTitle());
        toUpdate.setDescription(taskDto.getDescription());

        final var updated = taskRepository.save(toUpdate);
        return TaskMapper.toDTO(updated);
    }   

    public void cancelTask(final Long taskId) {
        final var toCancel = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        toCancel.cancel();
        taskRepository.save(toCancel);
    }
    
    public PaginatedResultDTO getAllTasks( final int page, final int size, final String sort, final String status) {
        final var sortParams = sort.split(",");
        final var sortField = sortParams[0];
        final var sortDirection = sortParams.length > 1 ? sortParams[1] : "asc";
        final var pageable = PageRequest.of(page, size,  Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        final var specification = TaskSpecification.create(status);
        final var tasks = taskRepository.findAll(pageable);
        final var totalElements = tasks.getTotalElements();
        final var totalPages = tasks.getTotalPages();
        final var taskDtos = tasks.getContent().stream().map(TaskMapper::toDTO).toList();
        return new PaginatedResultDTO().page(page).size(size).totalElements(totalElements).totalPages(totalPages).content(taskDtos);
    }
    

    @Transactional
    public TaskDTO getTask(final Long taskId) {
        return taskRepository.findById(taskId).map(TaskMapper::toDTO)
            .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    public  void executeTask(final Long taskId) {
        final var toExecute = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));

        toExecute.execute();
        taskRepository.save(toExecute);
    }

    public void completeTask(final Long taskId) {
        final var toComplete = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));

        toComplete.complete();
        taskRepository.save(toComplete);
    }
    
}
