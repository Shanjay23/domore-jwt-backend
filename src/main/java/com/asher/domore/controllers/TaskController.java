package com.asher.domore.controllers;

import com.asher.domore.models.Task;
import com.asher.domore.security.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/project/{projectId}")
    public List<Task> getTasksByProject(@PathVariable Long projectId) {
        return taskService.getTasksByProjectId(projectId);
    }

    @PreAuthorize("#assigneeId == authentication.principal.id")
    @GetMapping("/assignee/{assigneeId}")
    public List<Task> getTasksByAssignee(@PathVariable Long assigneeId) {
        return taskService.getTasksByAssigneeId(assigneeId);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }
}
