package de.semlaki.project_semlaki_be.security.sec_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO для запроса обновления токена.
 */
@Schema(description = "DTO для запроса обновления токена")
public record RefreshRequestDto(
        @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        @NotBlank(message = "Refresh token must not be blank")
        String refreshToken) {
}
