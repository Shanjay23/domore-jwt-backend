package com.asher.domore.services;

import java.util.List;
import java.util.Optional;

import com.asher.domore.dto.TaskDTO;
import com.asher.domore.models.User;
import com.asher.domore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.asher.domore.models.Task;
import com.asher.domore.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

	public List<Task> getTasksByProjectId(Long projectId) {
		return taskRepository.findByProjectId(projectId);
	}

	public List<Task> getTasksByAssigneeId(Long assigneeId) {
		return taskRepository.findByAssigneeId(assigneeId);
	}

	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	public ResponseEntity<?> getTaskById(Long id) {
		Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            TaskDTO dto = new TaskDTO();
            dto.setTitle(task.get().getTitle());
            dto.setDescription(task.get().getDescription());
            dto.setAssigneeId(task.get().getAssignee() != null ? task.get().getAssignee().getId() : null);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found with id: " + id);
	}

    public ResponseEntity<?> updateTask(Long id, TaskDTO task) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()){
            Task toUpdate = existingTask.get();
            toUpdate.setTitle(task.getTitle());
            toUpdate.setDescription(task.getDescription());
            if (task.getAssigneeId() != null){
                User assignee = userRepository.findById(task.getAssigneeId())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                if (assignee.getProject() == null) {
                    return ResponseEntity.badRequest().body("❌ Assignee has no project assigned");
                }
                toUpdate.setAssignee(assignee);
                toUpdate.setProject(assignee.getProject());
            }
            taskRepository.save(toUpdate);
            return ResponseEntity.ok("Task updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found with id: " + id);
    }
}
