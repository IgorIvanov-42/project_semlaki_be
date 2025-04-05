package de.semlaki.project_semlaki_be.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Глобальный обработчик исключений для REST API.
 * <p>
 * Этот класс перехватывает исключения, возникающие в контроллерах,
 * и возвращает структурированные ответы об ошибках.
 * Аннотация {@code @Hidden} скрывает этот контроллер от документации Swagger.
 * </p>
 */
@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключения типа {@link RestApiException} и формирует ответ с ошибкой.
     *
     * @param ex      исключение RestApiException, содержащее информацию об ошибке
     * @param request объект HttpServletRequest для получения URI запроса, вызвавшего ошибку
     * @return ResponseEntity с ErrorResponseDto и соответствующим HTTP статусом
     */
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponseDto> handleRestApiException(RestApiException ex, HttpServletRequest request) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                LocalDateTime.now(),
                ex.getStatus().value(),
                ex.getStatus().getReasonPhrase(),
                ex.getMessage(),
                List.of(),  // пустой список ошибок, т.к. это не ошибки валидации
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    /**
     * Обрабатывает исключения валидации (MethodArgumentNotValidException)
     * и формирует ответ с детальными сообщениями об ошибках для каждого поля.
     *
     * @param ex      исключение MethodArgumentNotValidException, содержащее ошибки валидации
     * @param request объект HttpServletRequest для получения URI запроса, вызвавшего ошибку
     * @return ResponseEntity с ErrorResponseDto, содержащим список ошибок валидации, и статусом BAD_REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, List<String>> fieldErrorsMap = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrorsMap.computeIfAbsent(fieldError.getField(), key -> new ArrayList<>())
                    .add(fieldError.getDefaultMessage());
        }

        List<ValidationErrorDto> validationErrors = fieldErrorsMap.entrySet().stream()
                .map(entry -> new ValidationErrorDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed for one or more fields",
                validationErrors,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
