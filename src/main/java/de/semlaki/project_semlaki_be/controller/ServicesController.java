package de.semlaki.project_semlaki_be.controller;

import de.semlaki.project_semlaki_be.domain.dto.ServiceCreateRequestDto;
import de.semlaki.project_semlaki_be.domain.dto.ServiceResponseDto;
import de.semlaki.project_semlaki_be.exception.ErrorResponseDto;
import de.semlaki.project_semlaki_be.service.ServicesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с сервисами.
 * <p>
 * Предоставляет API для получения списка сервисов, фильтрации, получения случайных сервисов,
 * получения сервиса по идентификатору, создания и удаления сервиса.
 * </p>
 */
@RestController
@RequestMapping("/services")
@Tag(name = "Сервисы", description = "API для работы с сервисами")
public class ServicesController {

    private final ServicesService servicesService;

    /**
     * Конструктор для внедрения сервиса.
     *
     * @param servicesService сервис для работы с сервисами
     */
    public ServicesController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    /**
     * Возвращает список всех сервисов.
     *
     * @return список объектов {@link ServiceResponseDto}
     */
    @GetMapping
    @Operation(
            summary = "Получение списка сервисов",
            description = "Метод возвращает список всех доступных сервисов.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сервисы успешно получены",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponseDto.class))
                    )
            }
    )
    public List<ServiceResponseDto> getAllServices() {
        return servicesService.getAllServices();
    }

    /**
     * Возвращает случайное подмножество сервисов.
     *
     * @param serviceCounter количество сервисов для возврата (по умолчанию 4)
     * @return список объектов {@link ServiceResponseDto}
     */
    @GetMapping("/random")
    @Operation(
            summary = "Получение случайных сервисов",
            description = "Метод возвращает случайное подмножество сервисов. Количество сервисов можно задать параметром.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сервисы успешно получены",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponseDto.class))
                    )
            }
    )
    public List<ServiceResponseDto> getRandomServices(
            @RequestParam(value = "service_counter", required = false, defaultValue = "4")
            @Parameter(description = "Количество сервисов для возврата", example = "4")
            int serviceCounter) {
        return servicesService.getRandomServices(serviceCounter);
    }

    /**
     * Возвращает список сервисов, удовлетворяющих поисковому запросу по названию или описанию.
     *
     * @param term поисковый запрос
     * @return список объектов {@link ServiceResponseDto}
     */
    @GetMapping("/filter")
    @Operation(
            summary = "Фильтрация сервисов",
            description = "Метод возвращает список сервисов, содержащих заданный поисковый запрос в названии или описании.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сервисы успешно получены",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponseDto.class))
                    )
            }
    )
    public List<ServiceResponseDto> getFilteredServices(
            @RequestParam("term")
            @Parameter(description = "Поисковый запрос", example = "repair")
            String term) {
        return servicesService.getServicesByTitle(term);
    }

    /**
     * Возвращает данные сервиса по заданному идентификатору.
     *
     * @param id идентификатор сервиса
     * @return объект {@link ServiceResponseDto} с данными сервиса
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение сервиса по идентификатору",
            description = "Метод возвращает данные сервиса по заданному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сервис успешно получен",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный формат идентификатора сервиса. Пример: ID должен быть положительным числом.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Service ID must be positive\",\"errors\":[{\"field\":\"id\",\"messages\":[\"Service ID must be positive\"]}],\"path\":\"/services/abc\"}"))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Сервис не найден. Пример: Сервис с ID 1 не найден.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Service not found\",\"errors\":[],\"path\":\"/services/1\"}"))
                    )
            }
    )
    public ServiceResponseDto getServiceById(
            @PathVariable
            @NotNull(message = "Service ID must not be null")
            @Positive(message = "Service ID must be positive")
            @Parameter(description = "Идентификатор сервиса", example = "1")
            Long id) {
        return servicesService.getServiceById(id);
    }

    /**
     * Возвращает список сервисов, принадлежащих заданной категории.
     *
     * @param categoryId идентификатор категории
     * @return список объектов {@link ServiceResponseDto}
     */
    @GetMapping("/category/{categoryId}")
    @Operation(
            summary = "Получение сервисов по категории",
            description = "Метод возвращает список сервисов, принадлежащих заданной категории.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сервисы успешно получены",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный формат идентификатора категории. Пример: ID должен быть положительным числом.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Category ID must be positive\",\"errors\":[{\"field\":\"categoryId\",\"messages\":[\"Category ID must be positive\"]}],\"path\":\"/services/category/abc\"}"))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Сервисы не найдены для указанной категории. Пример: Нет сервисов для категории с ID 1.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Services not found for category\",\"errors\":[],\"path\":\"/services/category/1\"}"))
                    )
            }
    )
    public List<ServiceResponseDto> getServicesByCategory(
            @PathVariable
            @NotNull(message = "Category ID must not be null")
            @Positive(message = "Category ID must be positive")
            @Parameter(description = "Идентификатор категории", example = "1")
            Long categoryId) {
        return servicesService.getServicesByCategory(categoryId);
    }

    /**
     * Возвращает список сервисов, принадлежащих текущему аутентифицированному пользователю.
     *
     * @param userEmail email текущего пользователя (получается из аутентификации)
     * @return список объектов {@link ServiceResponseDto}
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    @Operation(
            summary = "Получение сервисов текущего пользователя",
            description = "Метод возвращает список сервисов, принадлежащих аутентифицированному пользователю.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сервисы успешно получены",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не аутентифицирован. Пример: Необходимо авторизоваться для доступа к сервисам.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":401,\"error\":\"Unauthorized\",\"message\":\"User not authenticated\",\"errors\":[],\"path\":\"/services/user\"}"))
                    )
            }
    )
    public List<ServiceResponseDto> getServicesByUser(
            @AuthenticationPrincipal
            @Parameter(hidden = true)
            String userEmail) {
        return servicesService.getServicesByUser(userEmail);
    }

    /**
     * Создает новый сервис для текущего аутентифицированного пользователя.
     *
     * @param createServiceDto DTO с данными для создания сервиса
     * @param userEmail        email текущего пользователя (получается из аутентификации)
     * @return объект {@link ServiceResponseDto} с данными созданного сервиса
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @Operation(
            summary = "Создание нового сервиса",
            description = "Метод для создания нового сервиса для аутентифицированного пользователя.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сервис успешно создан",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации входных данных при создании сервиса. Пример: Поле title не может быть пустым.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Validation failed: Title must not be blank\",\"errors\":[{\"field\":\"title\",\"messages\":[\"Title must not be blank\"]}],\"path\":\"/services\"}"))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не аутентифицирован",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":401,\"error\":\"Unauthorized\",\"message\":\"User not authenticated\",\"errors\":[],\"path\":\"/services\"}"))
                    )
            }
    )
    public ServiceResponseDto createService(
            @RequestBody
            @Valid
            ServiceCreateRequestDto createServiceDto,
            @AuthenticationPrincipal @Parameter(hidden = true) String userEmail) {
        return servicesService.createService(createServiceDto, userEmail);
    }

    /**
     * Удаляет сервис по заданному идентификатору.
     *
     * @param id идентификатор сервиса
     * @return объект {@link ServiceResponseDto} удаленного сервиса
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление сервиса",
            description = "Метод для удаления сервиса по заданному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сервис успешно удален",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный формат идентификатора сервиса. Пример: ID должен быть положительным числом.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Service ID must be positive\",\"errors\":[{\"field\":\"id\",\"messages\":[\"Service ID must be positive\"]}],\"path\":\"/services/abc\"}"))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Сервис не найден. Пример: Сервис с ID 1 не найден.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Service not found\",\"errors\":[],\"path\":\"/services/1\"}"))
                    )
            }
    )
    public ServiceResponseDto deleteService(
            @PathVariable
            @NotNull(message = "Service ID must not be null")
            @Positive(message = "Service ID must be positive")
            @Parameter(description = "Идентификатор сервиса", example = "1")
            Long id) {
        return servicesService.deleteService(id);
    }
}
