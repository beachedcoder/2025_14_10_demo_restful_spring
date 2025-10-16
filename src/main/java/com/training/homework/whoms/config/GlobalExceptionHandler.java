package com.training.homework.whoms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for centralized error handling across all controllers.
 */
@ControllerAdvice
public final class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String GENERIC_ERROR_MESSAGE = "Our apologies for not being able to service your request at present";

    /**
     * Handles general exceptions that are not specifically handled elsewhere.
     *
     * @param exception the unhandled exception
     * @return ResponseEntity with generic error message and internal server error status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(final Exception exception) {
        logger.error("Unexpected error occurred while processing request", exception);
        
        final ErrorResponse errorResponse = new ErrorResponse(GENERIC_ERROR_MESSAGE);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handles JSON parsing errors when request body cannot be read.
     *
     * @param exception the message not readable exception
     * @return ResponseEntity with generic error message and bad request status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadableException(final HttpMessageNotReadableException exception) {
        logger.warn("Invalid JSON in request body: {}", exception.getMessage());
        
        final ErrorResponse errorResponse = new ErrorResponse(GENERIC_ERROR_MESSAGE);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles unsupported media type errors.
     *
     * @param exception the media type not supported exception
     * @return ResponseEntity with generic error message and unsupported media type status
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException exception) {
        logger.warn("Unsupported media type in request: {}", exception.getMessage());
        
        final ErrorResponse errorResponse = new ErrorResponse(GENERIC_ERROR_MESSAGE);
        
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorResponse);
    }

    /**
     * Represents a standardized error response structure.
     */
    public record ErrorResponse(String message) {}
}