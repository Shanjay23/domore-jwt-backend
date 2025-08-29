package com.asher.domore.security.services;

import com.asher.domore.models.Project;
import com.asher.domore.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Optional<Project> getProjectByOwnerId(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
}

