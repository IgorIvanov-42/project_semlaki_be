package de.semlaki.project_semlaki_be.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleRestApiException() {
        // Arrange
        RestApiException exception = new RestApiException("Test error", HttpStatus.BAD_REQUEST);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("/api/test");

        // Act
        ResponseEntity<ErrorResponseDto> response = handler.handleRestApiException(exception, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponseDto body = response.getBody();
        assertNotNull(body);
        assertEquals("Test error", body.message());
        assertEquals("/api/test", body.path());
        assertTrue(body.errors().isEmpty(), "Errors list should be empty");
    }

    // Аналогичным образом можно написать тесты для метода handleValidationException
}
