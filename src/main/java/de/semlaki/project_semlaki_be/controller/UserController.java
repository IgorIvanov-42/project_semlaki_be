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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для регистрации пользователей.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Регистрация пользователей", description = "API для регистрации новых пользователей")
public class UserController {

    private final UserService userService;

    /**
     * Конструктор для внедрения сервиса пользователей.
     *
     * @param userService сервис работы с пользователями.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Регистрирует нового пользователя.
     * <p>
     * При успешной регистрации возвращается объект {@link UserResponseDto}.
     * Если пользователь с указанным email уже существует, возвращается ошибка со статусом 409 (Conflict).
     * </p>
     *
     * @param request DTO с данными для регистрации пользователя.
     * @return зарегистрированный пользователь или сообщение об ошибке.
     */
    @PostMapping("/register")
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
    public UserResponseDto register(@RequestBody @Valid RegisterRequestDto request) {
        return userService.register(request);
    }

}
