package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.apifirst.model.SmsDataResponseDto;
import com.pragma.powerup.apifirst.model.SmsRequestDto;
import com.pragma.powerup.application.handler.ISmsHandler;
import com.pragma.powerup.application.mapper.ISmsMapper;
import com.pragma.powerup.domain.api.ISmsServicePort;
import com.pragma.powerup.domain.model.SmsModel;
import org.springframework.stereotype.Service;


@Service
public class SmsHandler implements ISmsHandler {

    private final ISmsServicePort smsServicePort;
    private final ISmsMapper smsMapper;

    public SmsHandler(ISmsServicePort smsServicePort, ISmsMapper smsMapper) {
        this.smsServicePort = smsServicePort;
        this.smsMapper = smsMapper;
    }

    @Override
    public SmsDataResponseDto sendSms(SmsRequestDto smsRequest) {
        SmsModel smsModel = smsServicePort.sendSms(
                smsRequest.getPhoneNumber(),
                smsRequest.getMessage(),
                smsRequest.getMetadata()
        );

        return smsMapper.toSmsDataResponse(smsModel);
    }
}

