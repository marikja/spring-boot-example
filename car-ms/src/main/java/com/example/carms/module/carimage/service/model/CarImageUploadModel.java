package com.example.carms.module.carimage.service.model;

import com.example.carms.module.carimage.constant.UploadImageStatus;

import java.util.UUID;

public record CarImageUploadModel(
        UUID carId,
        UploadImageStatus status,
        String imageName
) {
}
