package com.ureca.gate.global.domain;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Builder
public class CustomPage<T> {

    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public static <T, U> CustomPage<U> convert(CustomPage<T> sourcePage, Function<? super T, ? extends U> converter) {
        List<U> convertedContent = sourcePage.getContent()
                .stream()
                .map(converter)
                .collect(Collectors.toList());
        return CustomPage.<U>builder()
                .content(convertedContent)
                .page(sourcePage.page)
                .size(sourcePage.size)
                .totalElements(sourcePage.totalElements)
                .totalPages(sourcePage.totalPages)
                .build();
    }

    public static <T> CustomPage<T> from(Page<T> page) {
        return CustomPage.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
