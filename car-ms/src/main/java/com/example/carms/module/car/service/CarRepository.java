package com.example.carms.module.car.service;

import com.example.carms.module.car.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CarRepository extends JpaRepository<Car, UUID> {

    boolean existsByVin(String vin);
}
