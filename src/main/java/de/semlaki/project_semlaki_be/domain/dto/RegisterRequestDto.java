package de.semlaki.project_semlaki_be.domain.dto;

import de.semlaki.project_semlaki_be.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO для регистрации нового пользователя.
 */
public record RegisterRequestDto(
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be valid")
        @Schema(description = "Электронная почта пользователя", example = "user@example.com")
        String email,

        @NotBlank(message = "Password must not be blank")
        @Schema(description = "Пароль пользователя", example = "password123")
        String password,

        @NotBlank(message = "First name must not be blank")
        @Schema(description = "Имя пользователя", example = "John")
        String firstName,

        @NotBlank(message = "Last name must not be blank")
        @Schema(description = "Фамилия пользователя", example = "Doe")
        String lastName
) {

    /**
     * Преобразует DTO в сущность User.
     *
     * @return объект User
     */
    public User from() {
        return new User(email, firstName, lastName, password);
    }
}
