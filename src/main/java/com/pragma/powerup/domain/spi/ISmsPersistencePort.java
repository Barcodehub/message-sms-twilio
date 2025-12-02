package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.SmsModel;

import java.util.Map;


public interface ISmsPersistencePort {
    SmsModel sendSms(String phoneNumber, String message, Map<String, String> metadata);
}

