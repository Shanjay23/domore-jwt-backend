package com.asher.domore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectDTO {
    @NotBlank
    private String name;
    private String description;
    private Long ownerId;
}
