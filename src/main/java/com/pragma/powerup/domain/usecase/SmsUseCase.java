package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ISmsServicePort;
import com.pragma.powerup.domain.exception.InvalidPhoneNumberException;
import com.pragma.powerup.domain.model.SmsModel;
import com.pragma.powerup.domain.spi.ISmsPersistencePort;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Caso de uso para el envío de SMS
 * Implementa la lógica de negocio del dominio
 */
public class SmsUseCase implements ISmsServicePort {

    private final ISmsPersistencePort smsPersistencePort;

    // Patrón para validar números en formato E.164
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+[1-9]\\d{1,14}$");

    public SmsUseCase(ISmsPersistencePort smsPersistencePort) {
        this.smsPersistencePort = smsPersistencePort;
    }

    @Override
    public SmsModel sendSms(String phoneNumber, String message, Map<String, String> metadata) {
        // Validación del número de teléfono
        validatePhoneNumber(phoneNumber);

        // Validación del mensaje
        validateMessage(message);

        // Delegar el envío al adaptador de infraestructura
        return smsPersistencePort.sendSms(phoneNumber, message, metadata);
    }

    /**
     * Valida que el número de teléfono esté en formato E.164
     */
    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidPhoneNumberException("El número de teléfono no puede estar vacío");
        }

        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new InvalidPhoneNumberException(phoneNumber);
        }
    }

    /**
     * Valida que el mensaje no esté vacío
     */
    private void validateMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío");
        }

        if (message.length() > 1600) {
            throw new IllegalArgumentException("El mensaje no puede exceder los 1600 caracteres");
        }
    }
}

