package com.example.carms.car.service.model;

import com.example.carms.car.entity.Car;
import com.example.carms.rentcar.entity.RentCar;

public record RentCarModel(
        Car car,
        RentCar rentCar
) {
}
