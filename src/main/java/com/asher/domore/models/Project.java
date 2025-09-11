package com.asher.domore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String name;

	private String description;

	@OneToOne
	@JoinColumn(name = "owner_id")
	@JsonIgnore
    @EqualsAndHashCode.Exclude
	private User owner;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<User> members = new HashSet<>();

}
