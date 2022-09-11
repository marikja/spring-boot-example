package com.example.carms.module.carimage.service;

import com.example.carms.module.carimage.entity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CarImageRepository extends JpaRepository<CarImage, UUID> {
}
