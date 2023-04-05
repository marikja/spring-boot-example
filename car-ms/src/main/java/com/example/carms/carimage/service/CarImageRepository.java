package com.example.carms.carimage.service;

import com.example.carms.carimage.entity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CarImageRepository extends JpaRepository<CarImage, UUID> {
}
