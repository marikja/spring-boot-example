package com.example.carms.module.carimage.service;

import com.example.carms.module.carimage.entity.CarImage;
import com.example.carms.module.carimage.service.command.UploadImageCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Service
@RequiredArgsConstructor
public class CarImageUploaderService {

    private final CarImageRepository carImageRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void uploadImage(@Valid UploadImageCommand command) {
        final String imageName = command.image().getOriginalFilename();

        /* Image upload to cloud logic
        .
        .
        .
         */

        carImageRepository.save(new CarImage(command.carId(), imageName));
    }
}
