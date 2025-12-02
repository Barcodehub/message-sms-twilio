package com.pragma.powerup.application.mapper;

import com.pragma.powerup.apifirst.model.SmsDataResponseDto;
import com.pragma.powerup.apifirst.model.SmsResponseDto;
import com.pragma.powerup.domain.model.SmsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ISmsMapper {

    SmsResponseDto toSmsResponseDto(SmsModel smsModel);

    default SmsDataResponseDto toSmsDataResponse(SmsModel smsModel) {
        if (smsModel == null) {
            return null;
        }
        SmsResponseDto responseDto = toSmsResponseDto(smsModel);
        return new SmsDataResponseDto(responseDto);
    }

    default OffsetDateTime map(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }
}
