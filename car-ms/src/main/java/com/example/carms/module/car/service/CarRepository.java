package com.example.carms.module.car.service;

import com.example.carms.module.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

interface CarRepository extends JpaRepository<Car, UUID> {

    boolean existsByVin(String vin);

    List<Car> findAllByIdIn(Set<UUID> carIds);
}
