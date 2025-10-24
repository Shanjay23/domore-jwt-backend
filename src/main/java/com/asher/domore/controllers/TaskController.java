package com.asher.domore.controllers;

import java.util.List;

import com.asher.domore.dto.TaskDTO;
import com.asher.domore.dto.TaskResponse;
import com.asher.domore.models.User;
import com.asher.domore.repository.ProjectRepository;
import com.asher.domore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.asher.domore.models.Task;
import com.asher.domore.services.TaskService;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	@Autowired
	private TaskService taskService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

	@PreAuthorize("hasRole('ADMIN') or hasRole('TEAMLEAD')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getTask(@PathVariable Long id) {
		return taskService.getTaskById(id);
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('TEAMLEAD')")
	@GetMapping("/project/{projectId}")
	public List<Task> getTasksByProject(@PathVariable Long projectId) {
		return taskService.getTasksByProjectId(projectId);
	}

    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN') or hasRole('TEAMLEAD')")
    @GetMapping("/user/{userId}/tasks")
    public ResponseEntity<?> getTasksForUser(@PathVariable Long userId) {
        List<TaskResponse> tasks = taskService.getTasksByAssigneeId(userId).stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription()
                ))
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize("#assigneeId == authentication.principal.id or hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/assignee/{assigneeId}")
    public List<Task> getTasksByAssignee(@PathVariable Long assigneeId) {
        return taskService.getTasksByAssigneeId(assigneeId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TEAMLEAD')")
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskRequest) {
        User assignee = userRepository.findById(taskRequest.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (assignee.getProject() == null) {
            return ResponseEntity.badRequest().body("❌ Assignee has no project assigned");
        }

        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setAssignee(assignee);
        task.setProject(assignee.getProject());

        Task saved = taskService.saveTask(task);
        return ResponseEntity.ok(saved);
    }

    @PreAuthorize( "hasRole('ADMIN') or hasRole('TEAMLEAD')" )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO task){
        return taskService.updateTask(id, task);
    }

}
