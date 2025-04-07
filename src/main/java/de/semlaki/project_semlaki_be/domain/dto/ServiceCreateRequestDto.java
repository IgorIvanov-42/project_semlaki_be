package de.semlaki.project_semlaki_be.domain.dto;

import de.semlaki.project_semlaki_be.domain.entity.Categories;
import de.semlaki.project_semlaki_be.domain.entity.Services;
import de.semlaki.project_semlaki_be.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO для создания нового сервиса.
 */
public record ServiceCreateRequestDto(
        @NotBlank(message = "Title must not be blank")
        @Schema(description = "Название сервиса", example = "My Service")
        String title,

        @NotBlank(message = "Description must not be blank")
        @Schema(description = "Описание сервиса", example = "Service description")
        String description,

        @Schema(description = "Фотография сервиса", example = "service_photo.jpg")
        String photo,

        @NotNull(message = "Category ID must not be null")
        @Positive(message = "Category ID must be positive")
        @Schema(description = "Идентификатор категории", example = "1")
        Long categoryId
) {

    /**
     * Преобразует DTO в сущность Services.
     *
     * @param user     пользователь, создающий сервис
     * @param category категория сервиса
     * @return объект Services
     */
    public Services toEntity(User user, Categories category) {
        return new Services(title, user, description, photo, category);
    }
}
