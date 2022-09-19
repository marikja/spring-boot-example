package com.example.carms.module.car.service;

import com.example.carms.common.constant.DbTable;
import com.example.carms.common.service.PostgresLockService;
import com.example.carms.module.car.constant.CarType;
import com.example.carms.module.car.constant.LeasingCarProperties;
import com.example.carms.module.car.exception.CarAlreadyExistsException;
import com.example.carms.module.car.entity.Car;
import com.example.carms.module.car.service.action.CreateCarAction;
import com.example.carms.module.car.service.leasing.LeasingCalculateCabriolet;
import com.example.carms.module.car.service.leasing.LeasingCalculateCombi;
import com.example.carms.module.car.service.leasing.LeasingCalculateSedan;
import com.example.carms.module.car.service.leasing.LeasingCalculateSuv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class CarServiceTest {

    private final CarRepository carRepository = mock(CarRepository.class);
    private final CarFinderService carFinderService = mock(CarFinderService.class);
    private final LeasingCarProperties leasingCarProperties = mock(LeasingCarProperties.class);
    private final PostgresLockService postgresLockService = mock(PostgresLockService.class);
    private final LeasingCalculateCabriolet leasingCalculateCabriolet = mock(LeasingCalculateCabriolet.class);
    private final LeasingCalculateSedan leasingCalculateSedan = mock(LeasingCalculateSedan.class);
    private final LeasingCalculateCombi leasingCalculateCombi = mock(LeasingCalculateCombi.class);
    private final LeasingCalculateSuv leasingCalculateSuv = mock(LeasingCalculateSuv.class);
    private final CarService carService;

    private final static CreateCarAction CREATE_CAR_ACTION;

    static {
        CREATE_CAR_ACTION = new CreateCarAction(
                "vin",
                "make",
                "model",
                400,
                CarType.CABRIOLET,
                BigDecimal.valueOf(40000L)
        );
    }

    public CarServiceTest() {
        when(leasingCalculateCabriolet.getCarType()).thenReturn(CarType.CABRIOLET);
        when(leasingCalculateSedan.getCarType()).thenReturn(CarType.SEDAN);
        when(leasingCalculateCombi.getCarType()).thenReturn(CarType.COMBI);
        when(leasingCalculateSuv.getCarType()).thenReturn(CarType.SUV);

        carService = new CarService(
                carRepository,
                postgresLockService,
                carFinderService,
                leasingCarProperties,
                List.of(leasingCalculateCabriolet, leasingCalculateSedan, leasingCalculateCombi, leasingCalculateSuv)
        );

        verify(leasingCalculateCabriolet).getCarType();
        verify(leasingCalculateSedan).getCarType();
        verify(leasingCalculateCombi).getCarType();
        verify(leasingCalculateSuv).getCarType();
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(
                carRepository,
                leasingCalculateCabriolet,
                leasingCalculateSedan,
                leasingCalculateCombi,
                leasingCalculateSuv,
                carFinderService,
                leasingCarProperties
        );
    }

    @Test
    void testCreateCar_notExist_shouldCreateCar() {
        when(carRepository.existsByVin(any())).thenReturn(false);

        carService.create(CREATE_CAR_ACTION);

        verify(postgresLockService).lock(DbTable.CAR, List.of(CREATE_CAR_ACTION.vin()));

        verify(carRepository).existsByVin(CREATE_CAR_ACTION.vin());
        final ArgumentCaptor<Car> argumentCaptor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository).save(argumentCaptor.capture());
        final Car car = argumentCaptor.getValue();
        assertThat(car.getId()).isNotNull();
        assertThat(car.getVin()).isEqualTo(CREATE_CAR_ACTION.vin());
        assertThat(car.getMake()).isEqualTo(CREATE_CAR_ACTION.make());
        assertThat(car.getModel()).isEqualTo(CREATE_CAR_ACTION.model());
        assertThat(car.getHorsePower()).isEqualTo(CREATE_CAR_ACTION.horsePower());
        assertThat(car.getType()).isEqualTo(CREATE_CAR_ACTION.type());
        assertThat(car.getPrice()).isEqualTo(CREATE_CAR_ACTION.price());
    }

    @Test
    void testCreateCar_exist_shouldThrowException() {
        when(carRepository.existsByVin(any())).thenReturn(true);

        assertThatThrownBy(() -> carService.create(CREATE_CAR_ACTION))
                .isInstanceOf(CarAlreadyExistsException.class);

        verify(carRepository).existsByVin(CREATE_CAR_ACTION.vin());
    }
}
