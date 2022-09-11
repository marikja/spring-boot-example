package com.example.carms.module.rentcar.service;

import com.example.carms.common.constant.DbTable;
import com.example.carms.common.service.PostgresLockService;
import com.example.carms.module.rentcar.entity.RentCar;
import com.example.carms.module.rentcar.service.command.CreateRentCarCommand;
import com.example.carms.module.rentcar.service.command.ReturnCarCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Validated
@RequiredArgsConstructor
public class RentCarService {

    private final RentCarRepository rentCarRepository;
    private final RentCarFinderService rentCarFinderService;
    private final PostgresLockService postgresLockService;

    @Transactional
    public RentCar create(@Valid CreateRentCarCommand command) {
        postgresLockService.lock(DbTable.RENT_CAR, Collections.emptyList());

        final RentCar rentCar = new RentCar();
        rentCar.setCarId(command.carId());
        rentCar.setUserId(command.userId());
        rentCar.setFromDate(command.fromDate());
        rentCar.setToDate(command.toDate());

        return rentCarRepository.save(rentCar);
    }

    @Transactional
    public void returnCar(@Valid ReturnCarCommand command) {
        final RentCar rentCar = rentCarFinderService.getById(command.rentCarId());
        rentCar.setReturnedDate(LocalDateTime.now());

        rentCarRepository.save(rentCar);
    }
}
