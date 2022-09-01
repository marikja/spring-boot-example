package com.example.carms.module.rentcar.service;

import com.example.carms.module.rentcar.model.RentCar;
import com.example.carms.module.rentcar.service.command.CreateRentCarCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@RequiredArgsConstructor
public class RentCarService {

    private final RentCarRepository rentCarRepository;

    @Transactional
    public RentCar create(@Valid CreateRentCarCommand command) {
        final RentCar rentCar = new RentCar();
        rentCar.setCarId(command.carId());
        rentCar.setUserId(command.userId());
        rentCar.setFromDate(command.fromDate());
        rentCar.setToDate(command.toDate());
        return rentCarRepository.save(rentCar);
    }
}
