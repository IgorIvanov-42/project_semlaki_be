package de.semlaki.project_semlaki_be.controller;

import de.semlaki.project_semlaki_be.domain.dto.CategoryCreateDto;
import de.semlaki.project_semlaki_be.domain.dto.CategoryResponseDto;
import de.semlaki.project_semlaki_be.exception.ErrorResponseDto;
import de.semlaki.project_semlaki_be.service.CategoriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с категориями.
 * <p>
 * Предоставляет API для получения списка категорий, получения информации по конкретной категории,
 * создания новой категории и удаления категории.
 * </p>
 */
@RestController
@RequestMapping("/categories")
@Tag(name = "Категории", description = "API для работы с категориями")
public class CategoriesController {

    private final CategoriesService categoryService;

    /**
     * Конструктор для внедрения сервиса категорий.
     *
     * @param categoriesService сервис для работы с категориями.
     */
    public CategoriesController(CategoriesService categoriesService) {
        this.categoryService = categoriesService;
    }

    /**
     * Получает список всех категорий.
     *
     * @return список объектов {@link CategoryResponseDto}.
     */
    @GetMapping
    @Operation(
            summary = "Получение списка категорий",
            description = "Метод возвращает список всех доступных категорий.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список категорий успешно получен",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponseDto.class))
                    )
            }
    )
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Получает информацию о категории по идентификатору.
     *
     * @param id Идентификатор категории.
     * @return объект {@link CategoryResponseDto} с данными категории.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение категории по идентификатору",
            description = "Метод возвращает данные категории по заданному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Категория успешно получена",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный формат идентификатора категории. Пример: ID должен быть положительным числом.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Category ID must be positive\",\"errors\":[{\"field\":\"id\",\"messages\":[\"Category ID must be positive\"]}],\"path\":\"/categories/abc\"}"))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Категория не найдена. Пример: Категория с ID 1 не найдена.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Category not found\",\"errors\":[],\"path\":\"/categories/1\"}"))
                    )
            }
    )
    public CategoryResponseDto getCategoryById(
            @PathVariable
            @NotNull(message = "Category ID must not be null")
            @Positive(message = "Category ID must be positive")
            @Parameter(description = "Идентификатор категории", example = "1")
            Long id) {
        return categoryService.getCategoryById(id);
    }

    /**
     * Создает новую категорию.
     *
     * @param category DTO с данными для создания категории.
     * @return объект {@link CategoryResponseDto} с данными созданной категории.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создание новой категории",
            description = "Метод для создания новой категории на основе предоставленного DTO.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Категория успешно создана",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации входных данных. Пример: Поле title не может быть пустым.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Validation failed: Title must not be blank\",\"errors\":[{\"field\":\"title\",\"messages\":[\"Title must not be blank\"]}],\"path\":\"/categories\"}"))
                    )
            }
    )
    public CategoryResponseDto createCategory(
            @RequestBody
            @Valid
            @Parameter(description = "DTO для создания категории")
            CategoryCreateDto category) {
        return categoryService.createCategory(category);
    }

    /**
     * Удаляет категорию по идентификатору.
     *
     * @param id Идентификатор категории.
     * @return объект {@link CategoryResponseDto} удаленной категории.
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление категории",
            description = "Метод для удаления категории по заданному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Категория успешно удалена",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный формат идентификатора категории. Пример: ID должен быть положительным числом.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Category ID must be positive\",\"errors\":[{\"field\":\"id\",\"messages\":[\"Category ID must be positive\"]}],\"path\":\"/categories/xyz\"}"))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Категория не найдена. Пример: Категория с ID 1 не найдена.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Category not found\",\"errors\":[],\"path\":\"/categories/1\"}"))
                    )
            }
    )
    public CategoryResponseDto deleteCategory(
            @PathVariable
            @NotNull(message = "Category ID must not be null")
            @Positive(message = "Category ID must be positive")
            @Parameter(description = "Идентификатор категории", example = "1")
            Long id) {
        return categoryService.deleteCategory(id);
    }
}
