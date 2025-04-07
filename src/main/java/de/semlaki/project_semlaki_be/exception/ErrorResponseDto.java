package de.semlaki.project_semlaki_be.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "DTO для ответа об ошибке")
public record ErrorResponseDto(
        @Schema(description = "Время возникновения ошибки", example = "2025-04-05T15:30:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp,

        @Schema(description = "HTTP код статуса", example = "400")
        int status,

        @Schema(description = "Причина ошибки", example = "Bad Request")
        String error,

        @Schema(description = "Подробное сообщение об ошибке", example = "Validation failed for one or more fields")
        String message,

        @Schema(description = "Список деталей ошибок валидации")
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<ValidationErrorDto> errors,

        @Schema(description = "Путь запроса, вызвавшего ошибку", example = "/api/users")
        String path
) {
}
