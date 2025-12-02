package com.pragma.powerup.domain.model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Modelo de dominio para un mensaje SMS
 */
public class SmsModel {
    private String sid;
    private String phoneNumber;
    private String message;
    private String status;
    private LocalDateTime sentAt;
    private Map<String, String> metadata;

    public SmsModel() {
    }

    public SmsModel(String sid, String phoneNumber, String message, String status, LocalDateTime sentAt, Map<String, String> metadata) {
        this.sid = sid;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.status = status;
        this.sentAt = sentAt;
        this.metadata = metadata;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
