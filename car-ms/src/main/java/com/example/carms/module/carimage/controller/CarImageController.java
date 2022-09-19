package com.example.carms.module.carimage.controller;

import com.example.carms.module.carimage.service.CarImageService;
import com.example.carms.module.carimage.service.action.BatchUploadImageAction;
import com.example.carms.module.carimage.service.model.CarImageUploadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
        return carImageService.uploadImages(new BatchUploadImageAction(carId, images));
    }
}
