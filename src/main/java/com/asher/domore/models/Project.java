package com.asher.domore.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
	private User owner;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<User> members = new HashSet<>();


}
