package com.example.carms.module.car.service;

import com.example.carms.module.car.constant.CarType;
import com.example.carms.module.car.exception.CarAlreadyExistsException;
import com.example.carms.module.car.entity.Car;
import com.example.carms.module.car.service.command.CreateCarCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class CarServiceTest {

    private final CarRepository carRepository = mock(CarRepository.class);
    private final CarService carService = new CarService(carRepository);

    private final static CreateCarCommand CREATE_CAR_COMMAND;

    static {
        CREATE_CAR_COMMAND = new CreateCarCommand(
                "vin",
                "make",
                "model",
                400,
                CarType.CABRIOLET,
                BigDecimal.valueOf(40000L)
        );
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void testCreateCar_notExist_shouldCreateCar() {
        when(carRepository.existsByVin(any())).thenReturn(false);

        carService.create(CREATE_CAR_COMMAND);

        verify(carRepository).existsByVin(CREATE_CAR_COMMAND.vin());
        final ArgumentCaptor<Car> argumentCaptor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository).save(argumentCaptor.capture());
        final Car car = argumentCaptor.getValue();
        assertThat(car.getId()).isNotNull();
        assertThat(car.getVin()).isEqualTo(CREATE_CAR_COMMAND.vin());
        assertThat(car.getMake()).isEqualTo(CREATE_CAR_COMMAND.make());
        assertThat(car.getModel()).isEqualTo(CREATE_CAR_COMMAND.model());
        assertThat(car.getHorsePower()).isEqualTo(CREATE_CAR_COMMAND.horsePower());
        assertThat(car.getType()).isEqualTo(CREATE_CAR_COMMAND.type());
        assertThat(car.getPrice()).isEqualTo(CREATE_CAR_COMMAND.price());
    }

    @Test
    void testCreateCar_exist_shouldThrowException() {
        when(carRepository.existsByVin(any())).thenReturn(true);

        assertThatThrownBy(() -> carService.create(CREATE_CAR_COMMAND))
                .isInstanceOf(CarAlreadyExistsException.class);

        verify(carRepository).existsByVin(CREATE_CAR_COMMAND.vin());
    }
}
