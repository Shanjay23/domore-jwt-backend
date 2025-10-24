package com.asher.domore.controllers;

import com.asher.domore.dto.ProjectDTO;
import com.asher.domore.dto.ProjectEditDTO;
import com.asher.domore.dto.UserDTO;
import com.asher.domore.repository.UserRepository;
import com.asher.domore.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.asher.domore.models.Project;
import com.asher.domore.services.ProjectService;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*", maxAge = 3600)
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
        return projectService.getProject(id);
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

    @PreAuthorize("hasRole('ADMIN') or hasRole('TEAMLEAD')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectEditDTO dto) {
        return projectService.updateProject(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TEAMLEAD')")
    @GetMapping("/{id}/owner")
	public ResponseEntity<?> getProjectOwner(@PathVariable Long id) {
		return projectService.getProjectById(id)
				.map(project -> ResponseEntity.ok(project.getOwner()))
				.orElse(ResponseEntity.notFound().build());
	}

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/my/members")
    public ResponseEntity<?> getMyProjectMembers(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return projectService.getProjectByOwnerId(userId)
                .map(project -> {
                    Set<UserDTO> memberDTOs = project.getMembers()
                            .stream()
                            .map(user -> new UserDTO(
                                    user.getId(),
                                    user.getUsername(),
                                    user.getEmail(),
                                    user.getRoles()
                            ))
                            .collect(Collectors.toSet());

                    return ResponseEntity.ok(memberDTOs);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptySet()));
    }
}
