package de.semlaki.project_semlaki_be.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.semlaki.project_semlaki_be.exception.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Обработчик ошибок доступа (403 Forbidden).
 * Формирует JSON-ответ с информацией об ошибке.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    public CustomAccessDeniedHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Вызывается при отказе в доступе.
     *
     * @param request               объект HttpServletRequest
     * @param response              объект HttpServletResponse
     * @param accessDeniedException исключение, вызванное отсутствием прав
     * @throws IOException если произошла ошибка ввода/вывода
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                "Forbidden: " + accessDeniedException.getMessage(),
                List.of(),
                request.getRequestURI()
        );
        String json = mapper.writeValueAsString(errorResponse);
        response.getWriter().write(json);
    }
}
