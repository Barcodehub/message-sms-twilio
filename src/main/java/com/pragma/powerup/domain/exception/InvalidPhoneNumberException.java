package com.pragma.powerup.domain.exception;

/**
 * Excepción lanzada cuando el número de teléfono no es válido
 */
public class InvalidPhoneNumberException extends SmsException {
    public InvalidPhoneNumberException(String phoneNumber) {
        super("El número de teléfono no es válido: " + phoneNumber);
    }
}

