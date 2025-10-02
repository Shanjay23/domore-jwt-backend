package com.asher.domore.dto;

import lombok.Data;
import java.util.Set;

@Data
public class  UserDetailedDTO {
    private String username;
    private String email;
    private Set<String> roles;
    private Long projectId;
}
