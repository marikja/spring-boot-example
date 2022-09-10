package com.example.carms.module.car.service.model;

import com.example.carms.module.car.entity.Car;
import com.example.carms.module.rentcar.entity.RentCar;

public record RentCarModel(
        Car car,
        RentCar rentCar
) {
}
