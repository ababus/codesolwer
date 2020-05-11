package com.endava.internship.codesolver.logic.dto;

import java.util.Collection;

import com.endava.internship.codesolver.model.entities.Role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class UserRolesDTO {

    private String username;

    private String role = "NONE";

    private boolean active;

    public UserRolesDTO(String username, Collection<Role> roles, boolean active) {
        this.username = username;
        roles.stream().findFirst().ifPresent(userRole -> this.role = userRole.getName());
        this.active = active;
    }
}
