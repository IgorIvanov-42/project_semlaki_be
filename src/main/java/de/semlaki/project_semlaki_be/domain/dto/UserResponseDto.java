package de.semlaki.project_semlaki_be.domain.dto;

import de.semlaki.project_semlaki_be.domain.entity.Role;
import de.semlaki.project_semlaki_be.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO для ответа, содержащего данные пользователя")
public record UserResponseDto(
        @Schema(description = "Электронная почта пользователя", example = "user@example.com")
        String email,
        @Schema(description = "Имя пользователя", example = "John")
        String firstName,
        @Schema(description = "Фамилия пользователя", example = "Doe")
        String lastName,
        @Schema(description = "Список ролей пользователя", example = "[\"USER\", \"ADMIN\"]")
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
