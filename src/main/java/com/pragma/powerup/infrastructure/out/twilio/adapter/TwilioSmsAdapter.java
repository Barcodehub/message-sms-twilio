package com.pragma.powerup.infrastructure.out.twilio.adapter;

import com.pragma.powerup.domain.exception.SmsDeliveryException;
import com.pragma.powerup.domain.model.SmsModel;
import com.pragma.powerup.domain.spi.ISmsPersistencePort;
import com.pragma.powerup.infrastructure.configuration.TwilioProperties;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;


@Component
public class TwilioSmsAdapter implements ISmsPersistencePort {

    private static final Logger logger = LoggerFactory.getLogger(TwilioSmsAdapter.class);

    private final TwilioProperties twilioProperties;

    public TwilioSmsAdapter(TwilioProperties twilioProperties) {
        this.twilioProperties = twilioProperties;
    }


    @PostConstruct
    public void init() {
        Twilio.init(twilioProperties.getAccountSid(), twilioProperties.getAuthToken());
        logger.info("Twilio inicializado correctamente");
    }

    @Override
    public SmsModel sendSms(String phoneNumber, String message, Map<String, String> metadata) {
        try {
            Message twilioMessage = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioProperties.getPhoneNumber()),
                    message
            ).create();

            logger.info("SMS enviado exitosamente. SID: {}, Status: {}",
                    twilioMessage.getSid(), twilioMessage.getStatus());

            return mapToSmsModel(twilioMessage, phoneNumber, message, metadata);

        } catch (Exception e) {
            throw new SmsDeliveryException("Error al enviar el SMS: " + e.getMessage(), e);
        }
    }

    private SmsModel mapToSmsModel(Message twilioMessage, String phoneNumber, String message, Map<String, String> metadata) {
        SmsModel smsModel = new SmsModel();
        smsModel.setSid(twilioMessage.getSid());
        smsModel.setPhoneNumber(phoneNumber);
        smsModel.setMessage(message);
        smsModel.setStatus(twilioMessage.getStatus().toString().toUpperCase());

        if (twilioMessage.getDateCreated() != null) {
            smsModel.setSentAt(LocalDateTime.ofInstant(
                    twilioMessage.getDateCreated().toInstant(),
                    ZoneId.systemDefault()
            ));
        } else {
            smsModel.setSentAt(LocalDateTime.now());
        }

        smsModel.setMetadata(metadata);

        return smsModel;
    }
}

