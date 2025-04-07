package de.semlaki.project_semlaki_be.domain.dto;

import de.semlaki.project_semlaki_be.domain.entity.Categories;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO для создания новой категории.
 */
public record CategoryCreateDto(
        @NotBlank(message = "Title must not be blank")
        @Schema(description = "Название категории", example = "Electronics")
        String title,

        @Schema(description = "Описание категории", example = "Category for electronic products")
        String description
) {

    /**
     * Преобразует DTO в сущность Categories.
     *
     * @return объект Categories
     */
    public Categories toEntity() {
        return new Categories(this.title, this.description);
    }
}
