package com.example.carms.module.car.service;

import com.example.carms.common.util.LocalDateTimeFormatterUtil;
import com.example.carms.module.car.constant.CarType;
import com.example.carms.module.car.entity.Car;
import com.example.carms.module.rentcar.entity.RentCar;
import com.example.carms.module.rentcar.service.RentCarFinderService;
import com.example.carms.module.rentcar.service.RentCarService;
import com.example.carms.module.rentcar.service.action.CreateRentCarAction;
import com.proto.rentcar.RentCarGrpcRequest;
import com.proto.rentcar.RentCarGrpcResponse;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CarServiceGrpcImplTest {

    private final RentCarService rentCarService = mock(RentCarService.class);
    private final CarFinderService carFinderService = mock(CarFinderService.class);
    private final RentCarFinderService rentCarFinderService = mock(RentCarFinderService.class);

    private final CarServiceGrpcImpl carServiceGrpc = new CarServiceGrpcImpl(
            rentCarService,
            carFinderService,
            rentCarFinderService
    );

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(rentCarService, carFinderService, rentCarFinderService);
    }

    @Test
    void testRentCar() {
        final Car car = new Car();
        car.setVin("vin");
        car.setType(CarType.SEDAN);
        car.setPrice(BigDecimal.valueOf(123));
        car.setModel("model");
        car.setHorsePower(123);
        car.setMake("make");

        final RentCar rentCar = new RentCar();
        rentCar.setCarId(UUID.randomUUID());
        rentCar.setUserId(UUID.randomUUID());
        rentCar.setFromDate(LocalDateTimeFormatterUtil.fromString("2022-09-04T06:46"));
        rentCar.setToDate(LocalDateTimeFormatterUtil.fromString("2022-09-04T06:46"));

        when(carFinderService.findById(any())).thenReturn(Optional.of(car));
        when(rentCarService.create(any())).thenReturn(rentCar);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicReference<RentCarGrpcResponse> result = new AtomicReference<>();
        final StreamObserver<RentCarGrpcResponse> streamObserver = new StreamObserver<>() {
            @Override
            public void onNext(RentCarGrpcResponse rentCarGrpcResponse) {
                result.set(rentCarGrpcResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        };

        final RentCarGrpcRequest rentCarGrpcRequest = RentCarGrpcRequest.newBuilder()
                .setCarId(rentCar.getCarId().toString())
                .setUserId(rentCar.getUserId().toString())
                .setToDate(rentCar.getToDate().toString())
                .setFromDate(rentCar.getFromDate().toString())
                .build();
        carServiceGrpc.rentCar(rentCarGrpcRequest, streamObserver);

        final RentCarGrpcResponse rentCarGrpcResponse = result.get();
        assertThat(rentCarGrpcResponse.getCarId()).isEqualTo(rentCarGrpcRequest.getCarId());
        assertThat(rentCarGrpcResponse.getUserId()).isEqualTo(rentCarGrpcRequest.getUserId());
        assertThat(rentCarGrpcResponse.getToDate()).isEqualTo(rentCarGrpcRequest.getToDate());
        assertThat(rentCarGrpcResponse.getFromDate()).isEqualTo(rentCarGrpcRequest.getFromDate());
        assertThat(rentCarGrpcResponse.getHorsePower()).isEqualTo(car.getHorsePower());
        assertThat(rentCarGrpcResponse.getMake()).isEqualTo(car.getMake());
        assertThat(rentCarGrpcResponse.getPrice()).isEqualTo(car.getPrice().toString());
        assertThat(rentCarGrpcResponse.getModel()).isEqualTo(car.getModel());
        assertThat(rentCarGrpcResponse.getVin()).isEqualTo(car.getVin());

        verify(carFinderService).findById(rentCar.getCarId());

        final CreateRentCarAction action = new CreateRentCarAction(
                rentCar.getCarId(),
                rentCar.getUserId(),
                rentCar.getFromDate(),
                rentCar.getToDate()
        );
        verify(rentCarService).create(action);
    }
}