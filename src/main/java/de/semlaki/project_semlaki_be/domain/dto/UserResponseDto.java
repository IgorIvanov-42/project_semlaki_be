package de.semlaki.project_semlaki_be.domain.dto;

import de.semlaki.project_semlaki_be.domain.entity.Role;
import de.semlaki.project_semlaki_be.domain.entity.User;

import java.util.List;

public record UserResponseDto(
        String email,
        String firstName,
        String lastName,
        List<String> role
) {

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoles().stream().map(Role::getTitle).toList()
        );
    }
}

