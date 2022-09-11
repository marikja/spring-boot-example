package com.example.carms.module.carimage.controller;

import com.example.carms.module.car.service.CarService;
import com.example.carms.module.carimage.service.CarImageService;
import com.example.carms.module.carimage.service.command.BatchUploadImageCommand;
import com.example.carms.module.carimage.service.model.CarImageUploadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars/{carId}")
public class CarImageController {

    private final CarImageService carImageService;

    @PostMapping("/upload-images")
    public List<CarImageUploadModel> uploadImages(@PathVariable UUID carId, @RequestPart List<MultipartFile> images) {
        return carImageService.uploadImages(new BatchUploadImageCommand(carId, images));
    }
}