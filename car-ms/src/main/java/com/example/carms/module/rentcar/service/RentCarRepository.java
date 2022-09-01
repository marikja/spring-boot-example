package com.example.carms.module.rentcar.service;

import com.example.carms.module.rentcar.model.RentCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface RentCarRepository extends JpaRepository<RentCar, UUID> {
}
