package com.asher.domore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.asher.domore.models.Task;
import com.asher.domore.services.TaskService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	@Autowired
	private TaskService taskService;

	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getTask(@PathVariable Long id) {
		return taskService.getTaskById(id).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@GetMapping("/project/{projectId}")
	public List<Task> getTasksByProject(@PathVariable Long projectId) {
		return taskService.getTasksByProjectId(projectId);
	}

	@PreAuthorize("#assigneeId == authentication.principal.id or hasRole('ADMIN') or hasRole('MODERATOR')")
	@GetMapping("/assignee/{assigneeId}")
	public List<Task> getTasksByAssignee(@PathVariable Long assigneeId) {
		return taskService.getTasksByAssigneeId(assigneeId);
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@PostMapping
	public Task createTask(@RequestBody Task task) {
		return taskService.saveTask(task);
	}
}
