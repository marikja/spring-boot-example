package com.example.carms.car.service;

import com.example.carms.car.constant.CarType;
import com.example.carms.car.constant.LeasingCarProperties;
import com.example.carms.car.exception.CarAlreadyExistsException;
import com.example.carms.car.service.action.CreateCarAction;
import com.example.carms.car.service.leasing.LeasingCalculator;
import com.example.carms.common.constant.DbTable;
import com.example.carms.common.service.PostgresLockService;
import com.example.carms.car.entity.Car;
import com.example.carms.car.service.action.CalculateLeasingAction;
import com.example.carms.car.service.model.LeasingModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Validated
public class CarService {

    private final CarRepository carRepository;
    private final PostgresLockService postgresLockService;
    private final CarFinderService carFinderService;
    private final LeasingCarProperties leasingCarProperties;
    private final Map<CarType, LeasingCalculator> leasingCalculatorMap;

    public CarService(
            CarRepository carRepository,
            PostgresLockService postgresLockService,
            CarFinderService carFinderService,
            LeasingCarProperties leasingCarProperties,
            List<LeasingCalculator> leasingCalculators) {
        this.carRepository = carRepository;
        this.postgresLockService = postgresLockService;
        this.carFinderService = carFinderService;
        this.leasingCarProperties = leasingCarProperties;
        this.leasingCalculatorMap = leasingCalculators.stream()
                .collect(Collectors.toMap(LeasingCalculator::getCarType, Function.identity()));
    }

    @Transactional
    public Car create(@Valid CreateCarAction action) {
        postgresLockService.lock(DbTable.CAR, List.of(action.vin()));

        if (carRepository.existsByVin(action.vin())) {
            throw new CarAlreadyExistsException();
        }

        final Car car = new Car();
        car.setVin(action.vin());
        car.setMake(action.make());
        car.setType(action.type());
        car.setPrice(action.price());
        car.setHorsePower(action.horsePower());
        car.setModel(action.model());

        return carRepository.save(car);
    }

    public LeasingModel calculateLeasing(@Valid CalculateLeasingAction action) {
        final Car car = carFinderService.getById(action.carId());
        final BigDecimal leasingCoefficient = leasingCarProperties.getCoefficient(car.getType());
        final BigDecimal monthlyPayment = leasingCalculatorMap.get(car.getType()).calculateLeasing(
                leasingCoefficient, car.getPrice(), action.monthCount()
        );

        return new LeasingModel(car.getId(), action.monthCount(), car.getPrice(), monthlyPayment);
    }
}
