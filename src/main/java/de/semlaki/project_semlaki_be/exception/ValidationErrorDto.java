package de.semlaki.project_semlaki_be.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Детали ошибки валидации для конкретного поля")
public record ValidationErrorDto(
        @Schema(description = "Поле, в котором произошла ошибка", example = "email")
        String field,

        @Schema(description = "Список сообщений об ошибках для поля", example = "[\"must be a valid email address\"]")
        List<String> messages
) {
}
