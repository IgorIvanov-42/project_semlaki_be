package de.semlaki.project_semlaki_be.exception;

public class RestApiException extends RuntimeException {
    public RestApiException(String message) {
        super(message);
    }
}
