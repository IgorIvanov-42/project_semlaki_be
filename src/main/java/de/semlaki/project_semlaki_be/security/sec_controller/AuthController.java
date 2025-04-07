package de.semlaki.project_semlaki_be.security.sec_controller;

import de.semlaki.project_semlaki_be.domain.dto.UserResponseDto;
import de.semlaki.project_semlaki_be.exception.ErrorResponseDto;
import de.semlaki.project_semlaki_be.security.sec_dto.LoginRequestDto;
import de.semlaki.project_semlaki_be.security.sec_dto.RefreshRequestDto;
import de.semlaki.project_semlaki_be.security.sec_dto.TokenResponseDto;
import de.semlaki.project_semlaki_be.security.sec_service.AuthService;
import de.semlaki.project_semlaki_be.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для аутентификации и управления сессиями пользователей.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Аутентификация", description = "API для управления аутентификацией и сессиями пользователей")
public class AuthController {

    private final AuthService service;
    private final UserService userService;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param service     сервис аутентификации
     * @param userService сервис работы с пользователями
     */
    public AuthController(AuthService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    /**
     * Метод для входа пользователя в систему.
     * При успешной аутентификации возвращает access и refresh токены.
     *
     * @param user объект с данными для входа (email, password)
     * @return объект TokenResponseDto с accessToken и refreshToken
     * @throws AuthException в случае неудачной аутентификации
     */
    @PostMapping("/login")
    @Operation(
            summary = "Аутентификация пользователя",
            description = "Метод для аутентификации пользователя. При успешном входе возвращает access и refresh токены.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная аутентификация",
                            content = @Content(schema = @Schema(implementation = TokenResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Ошибка аутентификации",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )
    public TokenResponseDto login(@RequestBody
                                  @Valid
                                  LoginRequestDto user) throws AuthException {
        return service.login(user);
    }

    /**
     * Метод для обновления access токена по предоставленному refresh токену.
     *
     * @param refreshRequest объект с refresh токеном
     * @return объект TokenResponseDto с новым access токеном (refreshToken может быть null)
     */
    @PostMapping("/refresh")
    @Operation(
            summary = "Обновление access токена",
            description = "Метод для обновления access токена по предоставленному refresh токену.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Токен успешно обновлен",
                            content = @Content(schema = @Schema(implementation = TokenResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Ошибка аутентификации (недействительный refresh токен)",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )
    public TokenResponseDto getNewAccessToken(@RequestBody
                                              @Valid
                                              RefreshRequestDto refreshRequest) {
        return service.getNewAccessToken(refreshRequest.refreshToken());
    }

    /**
     * Метод для получения информации о текущем аутентифицированном пользователе.
     *
     * @return объект UserResponseDto с данными пользователя
     */
    @GetMapping("/profile")
    @Operation(
            summary = "Получение профиля пользователя",
            description = "Метод для получения информации о текущем аутентифицированном пользователе.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Профиль успешно получен",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не аутентифицирован",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )
    public UserResponseDto getProfile() {
        return userService.findCurrentUser();
    }
}
