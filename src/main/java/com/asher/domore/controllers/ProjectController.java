package com.asher.domore.controllers;

import com.asher.domore.dto.ProjectDTO;
import com.asher.domore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.asher.domore.models.Project;
import com.asher.domore.services.ProjectService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    UserRepository userRepository;

	@Autowired
	private ProjectService projectService;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getProject(@PathVariable Long id) {
		return projectService.getProjectById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/owner/{ownerId}")
	public ResponseEntity<?> getProjectByOwner(@PathVariable Long ownerId) {
		return projectService.getProjectByOwnerId(ownerId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Project createProject(@RequestBody ProjectDTO projectRequest) {
        return projectService.createProject(projectRequest);
    }

	@GetMapping("/{id}/owner")
	public ResponseEntity<?> getProjectOwner(@PathVariable Long id) {
		return projectService.getProjectById(id)
				.map(project -> ResponseEntity.ok(project.getOwner()))
				.orElse(ResponseEntity.notFound().build());
	}
}
