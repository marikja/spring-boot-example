package com.example.carms.car.service;

import com.example.carms.car.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {

    boolean existsByVin(String vin);
}
