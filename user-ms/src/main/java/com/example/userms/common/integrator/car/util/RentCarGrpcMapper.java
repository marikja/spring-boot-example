package com.example.userms.common.integrator.car.util;

import com.example.userms.common.integrator.car.constant.CarType;
import com.example.userms.common.integrator.car.model.response.RentCarResponse;
import com.example.userms.common.util.LocalDateTimeFormatterUtil;
import com.proto.rentcar.RentCarGrpcResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RentCarGrpcMapper {

    public static RentCarResponse map(RentCarGrpcResponse grpcResponse) {
        return new RentCarResponse(
                UUID.fromString(grpcResponse.getUserId()),
                UUID.fromString(grpcResponse.getCarId()),
                LocalDateTimeFormatterUtil.fromString(grpcResponse.getFromDate()),
                LocalDateTimeFormatterUtil.fromString(grpcResponse.getToDate()),
                grpcResponse.getVin(),
                grpcResponse.getMake(),
                grpcResponse.getModel(),
                CarType.valueOf(grpcResponse.getType().name()),
                new BigDecimal(grpcResponse.getPrice()));
    }
}
