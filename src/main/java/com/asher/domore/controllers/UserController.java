package com.asher.domore.controllers;

import java.util.List;

import com.asher.domore.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.asher.domore.models.User;
import com.asher.domore.services.UserService;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	// @PreAuthorize("hasRole('ADMIN') or hasRole('TEAMLEAD') or #id ==
	// authentication.principal.id")
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('TEAMLEAD')")
	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('TEAMLEAD')")
	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.saveUser(user);
	}

    @GetMapping("/{id}/projects")
    public ResponseEntity<?> getUserProjects(@PathVariable Long id) {
        Project project = userService.getUserProjectById(id);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Project not found for user ID: " + id);
        }
    }
}
