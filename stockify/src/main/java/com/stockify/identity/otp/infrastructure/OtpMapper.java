package com.stockify.identity.otp.infrastructure;

import com.stockify.identity.otp.domain.Otp;
import com.stockify.identity.otp.domain.OtpId;
import com.stockify.identity.otp.domain.OtpType;
import com.stockify.shared.vo.UserId;
import org.jspecify.annotations.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface OtpMapper {

    @Mapping(target = "id", expression = "java(otp.getId().value())")
    @Mapping(target = "userId", expression = "java(otp.getUserId().value())")
    @Mapping(target = "type", expression = "java(otp.getType().name())")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "expiresAt", source = "expiresAt")
    OtpRecord toRecord(Otp otp);

    default @NonNull Otp toDomain(@NonNull OtpRecord record) {
        return Otp.reconstitute(
                OtpId.of(record.id()),
                UserId.of(record.userId()),
                OtpType.valueOf(record.type()),
                record.code(),
                record.expiresAt()
        );
    }
}
