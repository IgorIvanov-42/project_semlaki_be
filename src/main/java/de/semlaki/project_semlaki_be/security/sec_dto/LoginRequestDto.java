package de.semlaki.project_semlaki_be.security.sec_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO для логина пользователя.
 */
@Schema(description = "DTO для логина пользователя")
public record LoginRequestDto(
        @NotBlank(message = "Email must not be blank")
        @Schema(description = "Электронная почта пользователя", example = "user@example.com")
        String email,

        @NotBlank(message = "Password must not be blank")
        @Schema(description = "Пароль пользователя", example = "password123")
        String password
) {}
