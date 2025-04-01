package de.semlaki.project_semlaki_be.domain.dto;

import de.semlaki.project_semlaki_be.domain.entity.Categories;

import java.util.Objects;

public record CategoryResponseDto(
        Long id,
        String title,
        String description,
        String photo
) {

    public static CategoryResponseDto toDto(Categories entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return new CategoryResponseDto(entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPhoto());
    }
}
