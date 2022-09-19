package com.example.userms.common.integrator.car;

import com.example.userms.common.integrator.car.model.request.FindAllRentCarsByUserIdRequest;
import com.example.userms.common.integrator.car.model.request.FindAllRentCarsByUserIdsRequest;
import com.example.userms.common.integrator.car.model.request.RentCarRequest;
import com.example.userms.common.integrator.car.model.request.ReturnCarRequest;
import com.example.userms.common.integrator.car.util.RentCarGrpcMapper;
import com.example.userms.common.integrator.car.model.response.RentCarResponse;
import com.proto.rentcar.CarServiceGrpc;
import com.proto.rentcar.FindAllByUserIdGrpcRequest;
import com.proto.rentcar.FindAllByUserIdsGrpcRequest;
import com.proto.rentcar.RentCarGrpcRequest;
import com.proto.rentcar.RentCarGrpcResponse;
import com.proto.rentcar.RentCarsGrpcResponse;
import com.proto.rentcar.ReturnCarGrpcRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CarIntegrator {

    private final CarServiceGrpc.CarServiceBlockingStub carServiceBlockingStub;

    public RentCarResponse rentCar(RentCarRequest request) {
        try {
            final RentCarGrpcResponse rentCarGrpcResponse = carServiceBlockingStub.rentCar(
                    RentCarGrpcRequest.newBuilder()
                            .setUserId(request.userId().toString())
                            .setCarId(request.carId().toString())
                            .setFromDate(request.fromDate().toString())
                            .setToDate(request.toDate().toString())
                            .build()
            );

            return RentCarGrpcMapper.map(rentCarGrpcResponse);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void returnCar(ReturnCarRequest request) {
        carServiceBlockingStub.returnCar(
                ReturnCarGrpcRequest.newBuilder()
                        .setRentCarId(request.rentCarId().toString())
                        .build()
        );
    }

    public List<RentCarResponse> findAllRentCarsByUserId(FindAllRentCarsByUserIdRequest request) {
        final RentCarsGrpcResponse rentCarsGrpcResponse = carServiceBlockingStub.findAllByUserId(
                FindAllByUserIdGrpcRequest.newBuilder()
                        .setUserId(request.userId().toString())
                        .build());

        return rentCarsGrpcResponse.getRentCarsList()
                .stream()
                .map(RentCarGrpcMapper::map)
                .toList();
    }

    public List<RentCarResponse> findAllRentCarsByUserIds(FindAllRentCarsByUserIdsRequest request) {
        final RentCarsGrpcResponse rentCarsGrpcResponse = carServiceBlockingStub.findAllByUserIds(
                FindAllByUserIdsGrpcRequest.newBuilder()
                        .addAllUserId(request.userIds().stream().map(UUID::toString).toList())
                        .build());

        return rentCarsGrpcResponse.getRentCarsList()
                .stream()
                .map(RentCarGrpcMapper::map)
                .toList();
    }
}
