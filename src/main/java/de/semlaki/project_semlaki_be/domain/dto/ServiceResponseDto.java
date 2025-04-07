package de.semlaki.project_semlaki_be.domain.dto;

import de.semlaki.project_semlaki_be.domain.entity.Services;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;

@Schema(description = "DTO для ответа, содержащего данные сервиса")
public record ServiceResponseDto(
        @Schema(description = "Уникальный идентификатор сервиса", example = "1")
        Long id,
        @Schema(description = "Название сервиса", example = "Service Title")
        String title,
        @Schema(description = "Описание сервиса", example = "Service description goes here")
        String description,
        @Schema(description = "Фотография сервиса", example = "service_photo.jpg")
        String photo,
        @Schema(description = "Данные владельца сервиса")
        UserResponseDto serviceOwner,
        @Schema(description = "Данные категории сервиса")
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
