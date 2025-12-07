package com.pragma.powerup.infrastructure.exceptionhandler;

import com.pragma.powerup.domain.exception.InvalidPhoneNumberException;
import com.pragma.powerup.domain.exception.SmsDeliveryException;
import com.pragma.powerup.domain.exception.SmsException;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String PATH = "path";


    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoDataFoundException(
            NoDataFoundException exception, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.NOT_FOUND.value());
        response.put(ERROR, "Not Found");
        response.put(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage());
        response.put(PATH, request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidPhoneNumberException(
            InvalidPhoneNumberException exception, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        response.put(ERROR, "Bad Request");
        response.put(MESSAGE, exception.getMessage());
        response.put(PATH, request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(SmsDeliveryException.class)
    public ResponseEntity<Map<String, Object>> handleSmsDeliveryException(
            SmsDeliveryException exception, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(ERROR, "SMS Delivery Failed");
        response.put(MESSAGE, exception.getMessage());
        response.put(PATH, request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    @ExceptionHandler(SmsException.class)
    public ResponseEntity<Map<String, Object>> handleSmsException(
            SmsException exception, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(ERROR, "SMS Error");
        response.put(MESSAGE, exception.getMessage());
        response.put(PATH, request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException exception, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        response.put(ERROR, "Validation Failed");
        response.put(MESSAGE, ExceptionResponse.VALIDATION_FAILED.getMessage());
        response.put("errors", errors);
        response.put(PATH, request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException exception, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        response.put(ERROR, "Bad Request");
        response.put(MESSAGE, exception.getMessage());
        response.put(PATH, request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(
            Exception exception, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.put(MESSAGE, ExceptionResponse.INTERNAL_SERVER_ERROR.getMessage());
        response.put(PATH, request.getDescription(false).replace("uri=", ""));

        // Log the actual exception for debugging (no exponer al cliente)
        exception.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
