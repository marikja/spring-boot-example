package com.example.carms.common.dto.response;

import java.util.List;

public record PageModel<T> (
    List<T> content,
    long elements,
    int pages
) {
}
