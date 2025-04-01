package de.semlaki.project_semlaki_be.domain.dto;

import de.semlaki.project_semlaki_be.domain.entity.Categories;
import de.semlaki.project_semlaki_be.domain.entity.Services;
import de.semlaki.project_semlaki_be.domain.entity.User;

import java.util.Objects;

public record ServiceResponseDto(
        Long id,
        String title,
        String description,
        String photo,
        UserResponseDto serviceOwner,
        CategoryResponseDto categoryDto
) {


    public static ServiceResponseDto toDto(Services entity) {

        if (Objects.isNull(entity)) {
            return null;
        }

        UserResponseDto serviceOwner = entity.getUser() != null ? UserResponseDto.toDto(entity.getUser()) : null;
        CategoryResponseDto categoryDto = entity.getCategory() != null ? CategoryResponseDto.toDto(entity.getCategory()) : null;
        return new ServiceResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPhoto(),
                serviceOwner,
                categoryDto
        );
    }
}
