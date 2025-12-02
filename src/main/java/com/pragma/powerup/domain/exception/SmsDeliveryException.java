package com.pragma.powerup.domain.exception;

/**
 * Excepción lanzada cuando falla el envío del SMS
 */
public class SmsDeliveryException extends SmsException {
    public SmsDeliveryException(String message) {
        super(message);
    }

    public SmsDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}

