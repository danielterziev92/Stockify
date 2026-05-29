package com.stockify.identity.otp.infrastructure;

import com.stockify.identity.otp.domain.Otp;
import com.stockify.identity.otp.domain.OtpId;
import com.stockify.identity.otp.domain.OtpType;
import com.stockify.shared.vo.UserId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
interface OtpMapper {

    @Mapping(target = "id", expression = "java(otp.getId().value())")
    @Mapping(target = "userId", expression = "java(otp.getUserId().value())")
    @Mapping(target = "type", expression = "java(otp.getType().name())")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "expiresAt", source = "expiresAt")
    OtpRecord toRecord(Otp otp);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "events", ignore = true)
    Otp toDomain(OtpRecord record);

    @ObjectFactory
    default Otp createOtp(OtpRecord record) {
        return Otp.reconstitute(
                OtpId.of(record.id()),
                UserId.of(record.userId()),
                OtpType.valueOf(record.type()),
                record.code(),
                record.expiresAt()
        );
    }
}
