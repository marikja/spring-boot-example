package com.example.carms.module.car.service;

import com.example.carms.common.constant.DbTable;
import com.example.carms.common.service.PostgresLockService;
import com.example.carms.module.car.exception.CarAlreadyExistsException;
import com.example.carms.module.car.entity.Car;
import com.example.carms.module.car.service.command.CreateCarCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final PostgresLockService postgresLockService;

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
}
