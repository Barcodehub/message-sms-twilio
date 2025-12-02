package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.apifirst.api.SmsApi;
import com.pragma.powerup.apifirst.model.SmsDataResponseDto;
import com.pragma.powerup.apifirst.model.SmsRequestDto;
import com.pragma.powerup.application.handler.ISmsHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SmsController implements SmsApi {

    private final ISmsHandler smsHandler;

    public SmsController(ISmsHandler smsHandler) {
        this.smsHandler = smsHandler;
    }

    @Override
    public ResponseEntity<SmsDataResponseDto> sendSms(@Valid SmsRequestDto smsRequestDto) {
        SmsDataResponseDto response = smsHandler.sendSms(smsRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

