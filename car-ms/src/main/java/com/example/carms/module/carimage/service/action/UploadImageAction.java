package com.example.carms.module.carimage.service.action;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record UploadImageAction(

        @NotNull
        UUID carId,

        @NotNull
        MultipartFile image
) {
}
