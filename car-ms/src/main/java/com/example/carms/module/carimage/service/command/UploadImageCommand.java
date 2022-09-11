package com.example.carms.module.carimage.service.command;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record UploadImageCommand(

        @NotNull
        UUID carId,

        @NotNull
        MultipartFile image
) {
}
