package com.example.carms.carimage.service;

import com.example.carms.carimage.constant.UploadImageStatus;
import com.example.carms.carimage.service.model.CarImageUploadModel;
import com.example.carms.carimage.service.action.BatchUploadImageAction;
import com.example.carms.carimage.service.action.UploadImageAction;
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
    public List<CarImageUploadModel> uploadImages(@Valid BatchUploadImageAction action) {
        final List<CarImageUploadModel> carImageUploadModels = new ArrayList<>();
        final UUID carId = action.carId();
        for (MultipartFile image: action.images()) {
            try {
                carImageUploaderService.uploadImage(new UploadImageAction(carId, image));
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
