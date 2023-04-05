package com.example.carms.rentcar.service;

import com.example.carms.common.constant.DbTable;
import com.example.carms.common.service.PostgresLockService;
import com.example.carms.rentcar.entity.RentCar;
import com.example.carms.rentcar.service.action.CreateRentCarAction;
import com.example.carms.rentcar.service.action.ReturnCarAction;
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
    public RentCar create(@Valid CreateRentCarAction action) {
        postgresLockService.lock(DbTable.RENT_CAR, Collections.emptyList());

        final RentCar rentCar = new RentCar();
        rentCar.setCarId(action.carId());
        rentCar.setUserId(action.userId());
        rentCar.setFromDate(action.fromDate());
        rentCar.setToDate(action.toDate());

        return rentCarRepository.save(rentCar);
    }

    @Transactional
    public void returnCar(@Valid ReturnCarAction action) {
        final RentCar rentCar = rentCarFinderService.getById(action.rentCarId());
        rentCar.setReturnedDate(LocalDateTime.now());

        rentCarRepository.save(rentCar);
    }
}
