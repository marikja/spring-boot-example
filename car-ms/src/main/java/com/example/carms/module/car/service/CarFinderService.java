package com.example.carms.module.car.service;

import com.example.carms.module.car.exception.CarNotFoundException;
import com.example.carms.module.car.model.Car;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarFinderService {

    private final CarRepository carRepository;

    public Car getById(UUID carId) {
        return carRepository.findById(carId).orElseThrow(CarNotFoundException::new);
    }

    public Page<Car> search(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

}
