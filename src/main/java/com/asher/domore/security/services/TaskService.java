package com.asher.domore.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asher.domore.models.Task;
import com.asher.domore.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	public List<Task> getTasksByProjectId(Long projectId) {
		return taskRepository.findByProjectId(projectId);
	}

	public List<Task> getTasksByAssigneeId(Long assigneeId) {
		return taskRepository.findByAssigneeId(assigneeId);
	}

	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	public Optional<Task> getTaskById(Long id) {
		return taskRepository.findById(id);
	}
}
