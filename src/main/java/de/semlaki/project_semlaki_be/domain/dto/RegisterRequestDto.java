package de.semlaki.project_semlaki_be.domain.dto;

import de.semlaki.project_semlaki_be.domain.entity.User;

public record RegisterRequestDto(
        String email,
        String password,
        String firstName,
        String lastName
) {

    public User from(){
        return new User(email, firstName, lastName, password);
    }
}
