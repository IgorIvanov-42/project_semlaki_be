package de.semlaki.project_semlaki_be.controller;

import de.semlaki.project_semlaki_be.domain.dto.RegisterRequestDto;
import de.semlaki.project_semlaki_be.domain.dto.UserResponseDto;
import de.semlaki.project_semlaki_be.exception.ErrorResponseDto;
import de.semlaki.project_semlaki_be.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для регистрации новых пользователей.
 * <p>
 * Предоставляет API для создания нового пользователя.
 * </p>
 */
@RestController
@RequestMapping("/register")
@Tag(name = "Регистрация", description = "API для регистрации новых пользователей")
public class RegistrationController {

    private final UserService service;

    /**
     * Конструктор для внедрения сервиса пользователей.
     *
     * @param service сервис для работы с пользователями.
     */
    public RegistrationController(UserService service) {
        this.service = service;
    }

    /**
     * Регистрирует нового пользователя.
     * <p>
     * При успешной регистрации возвращается объект {@link UserResponseDto} с данными нового пользователя.
     * Если пользователь с указанным email уже существует, возвращается ошибка со статусом 409 (Conflict).
     * </p>
     *
     * @param user DTO с данными для регистрации пользователя.
     * @return объект {@link UserResponseDto} с информацией о зарегистрированном пользователе.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(            summary = "Регистрация нового пользователя",
            description = "Метод для регистрации нового пользователя. При успешной регистрации возвращается информация о пользователе. Если пользователь с таким email уже существует, возвращается ошибка.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Пользователь успешно зарегистрирован",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Пользователь с таким email уже существует",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":409,\"error\":\"Conflict\",\"message\":\"Пользователь с таким email уже существует\",\"errors\":[],\"path\":\"/register\"}"))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации входных данных",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class,
                                            example = "{\"timestamp\":\"2025-04-05T15:30:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Ошибка валидации: email must not be blank\",\"errors\":[{\"field\":\"email\",\"messages\":[\"Email must not be blank\"]}],\"path\":\"/register\"}"))
                    )
            }
    )
    public UserResponseDto register(@RequestBody
                                    @Valid
                                    RegisterRequestDto user) {
        return service.register(user);
    }
}
