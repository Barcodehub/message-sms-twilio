package com.pragma.powerup.application.handler;

import com.pragma.powerup.apifirst.model.SmsDataResponseDto;
import com.pragma.powerup.apifirst.model.SmsRequestDto;

public interface ISmsHandler {

    SmsDataResponseDto sendSms(SmsRequestDto smsRequest);
}


