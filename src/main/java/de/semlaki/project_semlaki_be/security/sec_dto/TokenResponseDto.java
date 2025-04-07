package de.semlaki.project_semlaki_be.security.sec_dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для ответа с токенами.
 */
@Schema(description = "DTO для ответа с токенами")
public record TokenResponseDto(
        @Schema(description = "Access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,
        @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String refreshToken) {
}
