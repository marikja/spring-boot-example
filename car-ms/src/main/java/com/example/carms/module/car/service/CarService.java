package com.example.carms.module.car.service;

import com.example.carms.module.car.exception.CarAlreadyExistsException;
import com.example.carms.module.car.model.Car;
import com.example.carms.module.car.service.command.CreateCarCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @Transactional
    public Car create(@Valid CreateCarCommand command) {
        if(carRepository.existsByVin(command.vin())) {
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
