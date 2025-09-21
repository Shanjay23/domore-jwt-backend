package com.asher.domore.dto;

import lombok.Data;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;

    public TaskResponse(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
