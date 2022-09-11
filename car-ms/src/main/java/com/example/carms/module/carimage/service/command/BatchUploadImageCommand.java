package com.example.carms.module.carimage.service.command;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record BatchUploadImageCommand(

        @NotNull
        UUID carId,

        @NotNull
        List<MultipartFile> images
) {
}
