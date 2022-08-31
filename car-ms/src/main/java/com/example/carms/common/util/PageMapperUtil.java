package com.example.carms.common.util;

import com.example.carms.common.dto.response.PageModel;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.List;

@UtilityClass
public class PageMapperUtil {

    public static <P, L> PageModel<L> map(Page<P> page, List<L> list) {
        return new PageModel<>(list, page.getTotalElements(), page.getTotalPages());
    }
}
