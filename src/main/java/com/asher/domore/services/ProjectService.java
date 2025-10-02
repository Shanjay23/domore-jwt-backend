package com.asher.domore.services;

import java.util.Optional;

import com.asher.domore.dto.ProjectEditDTO;
import com.asher.domore.models.User;
import com.asher.domore.dto.ProjectDTO;
import com.asher.domore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.asher.domore.models.Project;
import com.asher.domore.repository.ProjectRepository;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

	public Optional<Project> getProjectByOwnerId(Long ownerId) {
		return projectRepository.findByOwnerId(ownerId);
	}

	public Project saveProject(Project project) {
		return projectRepository.save(project);
	}

	public Optional<Project> getProjectById(Long id) {
		return projectRepository.findById(id);
	}

    public Project createProject(ProjectDTO projectRequest) {
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());

        if (projectRequest.getOwnerId() != null) {
            User owner = userRepository.findById(projectRequest.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
            project.setOwner(owner);
        }
        return saveProject(project) ;
    }

    public ResponseEntity<?> updateProject(Long id, ProjectEditDTO dto) {
        var existing = getProjectById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Project project = existing.get();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        if (dto.getOwnerId() != null) {
            userRepository.findById(dto.getOwnerId()).ifPresent(project::setOwner);
        }
        Project saved = saveProject(project);
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> getProject(Long id) {
        Project project = getProjectById(id).orElse(null);
        if (project != null) {
            ProjectEditDTO projectDTO = new ProjectEditDTO();
            projectDTO.setName(project.getName());
            projectDTO.setDescription(project.getDescription());
            if (project.getOwner() != null) {
                projectDTO.setOwnerId(project.getOwner().getId());
            }
            return ResponseEntity.ok(projectDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found with id: " + id);
    }
}
