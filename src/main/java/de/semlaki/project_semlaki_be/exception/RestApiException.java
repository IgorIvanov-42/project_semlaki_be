package de.semlaki.project_semlaki_be.exception;

import org.springframework.http.HttpStatus;

public class RestApiException extends RuntimeException {

  private final HttpStatus status;

    public RestApiException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public RestApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
