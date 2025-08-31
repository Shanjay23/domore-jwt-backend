package com.asher.domore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asher.domore.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	Optional<Project> findByOwnerId(Long ownerId);
}
