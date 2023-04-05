package com.example.userms.user.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UserRentCarRequest(
        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
        LocalDateTime fromDate,

        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
        LocalDateTime toDate
) {
}
