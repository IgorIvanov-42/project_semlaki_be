package de.semlaki.project_semlaki_be.domain.dto;

import de.semlaki.project_semlaki_be.domain.entity.Categories;
import de.semlaki.project_semlaki_be.domain.entity.Services;
import de.semlaki.project_semlaki_be.domain.entity.User;

public record ServiceCreateRequestDto(
        String title,
        String description,
        long categoryId
) {

    public Services toEntity(ServiceCreateRequestDto dto, User user, Categories category) {
        return new Services(dto.title, user, dto.description, category);
    }
}
