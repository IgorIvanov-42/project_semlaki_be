package de.semlaki.project_semlaki_be.domain.dto;

import de.semlaki.project_semlaki_be.domain.entity.Categories;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для ответа, содержащего данные категории")
public record CategoryResponseDto(
        @Schema(description = "Уникальный идентификатор категории", example = "1")
        Long id,
        @Schema(description = "Название категории", example = "Electronics")
        String title,
        @Schema(description = "Описание категории", example = "Category for electronic products")
        String description,
        @Schema(description = "Ссылка на фотографию категории", example = "category_photo.jpg")
        String photo
) {

    public static CategoryResponseDto toDto(Categories entity) {
        if (entity == null) {
            return null;
        }
        return new CategoryResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPhoto());
    }
}
