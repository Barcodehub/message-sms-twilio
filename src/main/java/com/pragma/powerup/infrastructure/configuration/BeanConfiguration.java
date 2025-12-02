package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ISmsServicePort;
import com.pragma.powerup.domain.spi.ISmsPersistencePort;
import com.pragma.powerup.domain.usecase.SmsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfiguration {

    @Bean
    public ISmsServicePort smsServicePort(ISmsPersistencePort smsPersistencePort) {
        return new SmsUseCase(smsPersistencePort);
    }
}

