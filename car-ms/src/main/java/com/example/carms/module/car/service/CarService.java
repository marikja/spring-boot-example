package com.example.carms.module.car.service;

import com.example.carms.common.constant.DbTable;
import com.example.carms.common.service.PostgresLockService;
import com.example.carms.module.car.constant.CarType;
import com.example.carms.module.car.constant.LeasingCarProperties;
import com.example.carms.module.car.exception.CarAlreadyExistsException;
import com.example.carms.module.car.entity.Car;
import com.example.carms.module.car.service.command.CalculateLeasingCommand;
import com.example.carms.module.car.service.command.CreateCarCommand;
import com.example.carms.module.car.service.leasing.LeasingCalculator;
import com.example.carms.module.car.service.model.LeasingModel;
import lombok.RequiredArgsConstructor;
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
    public Car create(@Valid CreateCarCommand command) {
        postgresLockService.lock(DbTable.CAR, List.of(command.vin()));

        if (carRepository.existsByVin(command.vin())) {
            throw new CarAlreadyExistsException();
        }

        final Car car = new Car();
        car.setVin(command.vin());
        car.setMake(command.make());
        car.setType(command.type());
        car.setPrice(command.price());
        car.setHorsePower(command.horsePower());
        car.setModel(command.model());

        return carRepository.save(car);
    }

    public LeasingModel calculateLeasing(@Valid CalculateLeasingCommand command) {
        final Car car = carFinderService.getById(command.carId());
        if (car.getPrice() == null) {
            return null;
        }

        final BigDecimal leasingCoefficient = leasingCarProperties.getCoefficient(car.getType());

        final BigDecimal monthlyPayment = leasingCalculatorMap.get(car.getType()).calculateLeasing(
                leasingCoefficient, car.getPrice(), command.monthCount()
        );

        return new LeasingModel(car.getId(), command.monthCount(), car.getPrice(), monthlyPayment);
    }
}
