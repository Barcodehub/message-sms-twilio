package com.pragma.powerup.domain.exception;

/**
 * Excepción base para errores relacionados con SMS
 */
public class SmsException extends RuntimeException {
    public SmsException(String message) {
        super(message);
    }

    public SmsException(String message, Throwable cause) {
        super(message, cause);
    }
}

