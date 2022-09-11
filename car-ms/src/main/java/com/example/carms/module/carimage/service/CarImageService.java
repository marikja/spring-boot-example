package com.example.carms.module.carimage.service;

import com.example.carms.module.carimage.constant.UploadImageStatus;
import com.example.carms.module.carimage.service.command.BatchUploadImageCommand;
import com.example.carms.module.carimage.service.command.UploadImageCommand;
import com.example.carms.module.carimage.service.model.CarImageUploadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class CarImageService {

    private final CarImageUploaderService carImageUploaderService;

    @Transactional
    public List<CarImageUploadModel> uploadImages(@Valid BatchUploadImageCommand command) {
        final List<CarImageUploadModel> carImageUploadModels = new ArrayList<>();
        final UUID carId = command.carId();
        for (MultipartFile image: command.images()) {
            try {
                carImageUploaderService.uploadImage(new UploadImageCommand(carId, image));
                carImageUploadModels.add(
                        new CarImageUploadModel(carId, UploadImageStatus.COMPLETED, image.getOriginalFilename())
                );
            } catch (Exception e) {
                carImageUploadModels.add(
                        new CarImageUploadModel(carId, UploadImageStatus.FAILED, image.getOriginalFilename())
                );
            }

        }

        return carImageUploadModels;
    }
}
