package de.semlaki.project_semlaki_be.service.interfaces;

import de.semlaki.project_semlaki_be.domain.dto.RegisterRequestDto;
import de.semlaki.project_semlaki_be.domain.dto.UserResponseDto;
import de.semlaki.project_semlaki_be.domain.entity.User;
import de.semlaki.project_semlaki_be.exception.RestApiException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * Регистрирует нового пользователя.
     * <p>
     * Перед регистрацией производится нормализация email (приведение к нижнему регистру и удаление лишних пробелов).
     * Если пользователь с таким email уже существует, выбрасывается {@link RestApiException} с кодом {@link HttpStatus#CONFLICT}.
     * </p>
     *
     * @param user DTO с данными для регистрации
     * @return объект {@link UserResponseDto} с данными зарегистрированного пользователя
     * @throws RestApiException если пользователь с таким email уже существует
     */
    UserResponseDto register(RegisterRequestDto user);

    /**
     * Возвращает данные текущего аутентифицированного пользователя.
     *
     * @return объект {@link UserResponseDto} с данными текущего пользователя
     * @throws RestApiException если пользователь не найден
     */
    UserResponseDto findCurrentUser();

    /**
     * Ищет пользователя по email.
     * <p>
     * Если пользователь не найден, выбрасывается {@link RestApiException} с кодом {@link HttpStatus#NOT_FOUND}.
     * </p>
     *
     * @param userEmail email пользователя
     * @return объект {@link User} найденного пользователя
     * @throws RestApiException если пользователь не найден
     */
    User findOrThrow(String userEmail);
}
