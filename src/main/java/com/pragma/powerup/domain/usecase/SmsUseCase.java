package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ISmsServicePort;
import com.pragma.powerup.domain.exception.InvalidPhoneNumberException;
import com.pragma.powerup.domain.model.SmsModel;
import com.pragma.powerup.domain.spi.ISmsPersistencePort;

import java.util.Map;
import java.util.regex.Pattern;


public class SmsUseCase implements ISmsServicePort {

    private final ISmsPersistencePort smsPersistencePort;

    // formato E.164
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+[1-9]\\d{1,14}$");

    public SmsUseCase(ISmsPersistencePort smsPersistencePort) {
        this.smsPersistencePort = smsPersistencePort;
    }

    @Override
    public SmsModel sendSms(String phoneNumber, String message, Map<String, String> metadata) {
        validatePhoneNumber(phoneNumber);
        validateMessage(message);

        return smsPersistencePort.sendSms(phoneNumber, message, metadata);
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidPhoneNumberException("El número de teléfono no puede estar vacío");
        }

        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new InvalidPhoneNumberException(phoneNumber);
        }
    }


    private void validateMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío");
        }

        if (message.length() > 1600) {
            throw new IllegalArgumentException("El mensaje no puede exceder los 1600 caracteres");
        }
    }
}

