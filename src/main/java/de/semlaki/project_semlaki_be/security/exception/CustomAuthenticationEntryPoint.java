package de.semlaki.project_semlaki_be.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.semlaki.project_semlaki_be.exception.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Обработчик ошибок аутентификации (401 Unauthorized).
 * Формирует JSON-ответ с информацией об ошибке.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Вызывается при ошибке аутентификации.
     *
     * @param request       объект HttpServletRequest
     * @param response      объект HttpServletResponse
     * @param authException исключение аутентификации
     * @throws IOException если произошла ошибка ввода/вывода
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "Unauthorized: " + authException.getMessage(),
                List.of(),
                request.getRequestURI()
        );
        String json = mapper.writeValueAsString(errorResponse);
        response.getWriter().write(json);
    }
}
