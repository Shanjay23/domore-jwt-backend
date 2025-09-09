package com.asher.domore.services;

import java.util.Optional;

import com.asher.domore.models.User;
import com.asher.domore.dto.ProjectDTO;
import com.asher.domore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    //create project
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
}
