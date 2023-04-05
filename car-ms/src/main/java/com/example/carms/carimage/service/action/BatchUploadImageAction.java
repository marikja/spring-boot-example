package com.example.carms.carimage.service.action;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record BatchUploadImageAction(

        @NotNull
        UUID carId,

        @NotNull
        List<MultipartFile> images
) {
}
