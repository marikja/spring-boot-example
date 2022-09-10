package com.example.carms.module.car.service;

import com.example.carms.module.car.constant.CarType;
import com.example.carms.module.car.exception.CarNotFoundException;
import com.example.carms.module.car.entity.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class CarFinderServiceTest {

    private final CarRepository carRepository = mock(CarRepository.class);
    private final CarFinderService carFinderService = new CarFinderService(carRepository);


    private final static Car CAR;
    static {
        CAR = new Car();
        CAR.setVin("vin");
        CAR.setMake("make");
        CAR.setModel("model");
        CAR.setHorsePower(400);
        CAR.setType(CarType.CABRIOLET);
        CAR.setPrice(BigDecimal.valueOf(40000L));
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void testFindById_exist_shouldReturnUser() {
        final UUID carId = UUID.randomUUID();
        when(carRepository.findById(any())).thenReturn(Optional.of(CAR));

        carFinderService.getById(carId);

        verify(carRepository).findById(carId);
    }

    @Test
    void testFindById_noExist_shouldReturnUser() {
        final UUID carId = UUID.randomUUID();
        when(carRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> carFinderService.getById(carId)).isInstanceOf(CarNotFoundException.class);

        verify(carRepository).findById(carId);
    }
}
