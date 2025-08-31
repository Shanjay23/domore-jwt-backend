package com.asher.domore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String title;

	private String description;

	@ManyToOne
	@JoinColumn(name = "project_id")
	@JsonIgnore
	private Project project;

	@ManyToOne
	@JoinColumn(name = "assignee_id")
	@JsonIgnore
	private User assignee;
}
