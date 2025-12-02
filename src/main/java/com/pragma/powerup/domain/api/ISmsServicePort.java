package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.SmsModel;

import java.util.Map;

/**
 * Puerto de entrada (API) para el servicio de SMS
 * Define los casos de uso del dominio
 */
public interface ISmsServicePort {
    /**
     * Envía un mensaje SMS a un número de teléfono
     *
     * @param phoneNumber Número de teléfono destino en formato internacional
     * @param message Contenido del mensaje
     * @param metadata Metadatos opcionales del mensaje
     * @return Modelo con la información del SMS enviado
     */
    SmsModel sendSms(String phoneNumber, String message, Map<String, String> metadata);
}


