package com.example.carms.carimage.service.model;

import com.example.carms.carimage.constant.UploadImageStatus;

import java.util.UUID;

public record CarImageUploadModel(
        UUID carId,
        UploadImageStatus status,
        String imageName
) {
}
