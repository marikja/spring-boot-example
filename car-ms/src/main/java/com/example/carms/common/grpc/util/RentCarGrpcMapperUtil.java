package com.example.carms.common.grpc.util;

import com.example.carms.module.car.entity.Car;
import com.example.carms.module.car.service.model.RentCarModel;
import com.example.carms.module.rentcar.entity.RentCar;
import com.proto.rentcar.CarType;
import com.proto.rentcar.RentCarGrpcResponse;
import com.proto.rentcar.RentCarsGrpcResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RentCarGrpcMapperUtil {

    public static RentCarGrpcResponse map(RentCar rentCar, Car car) {
        return RentCarGrpcResponse.newBuilder()
                .setCarId(rentCar.getCarId().toString())
                .setUserId(rentCar.getUserId().toString())
                .setFromDate(rentCar.getFromDate().toString())
                .setToDate(rentCar.getToDate().toString())
                .setHorsePower(car.getHorsePower())
                .setPrice(String.valueOf(car.getPrice()))
                .setMake(car.getMake())
                .setType(CarType.valueOf(car.getType().name()))
                .setModel(car.getModel())
                .setVin(car.getVin())
                .build();
    }

    public static RentCarsGrpcResponse map(List<RentCarModel> rentCarModels) {
        List<RentCarGrpcResponse> rentCarGrpcResponses =  rentCarModels.stream()
                .map(model -> map(model.rentCar(), model.car()))
                .toList();

        return RentCarsGrpcResponse.newBuilder()
                .addAllRentCars(rentCarGrpcResponses)
                .build();
    }
}
