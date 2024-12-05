package com.ureca.gate.global.dto.response;

import com.ureca.gate.global.domain.CustomPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class PageResponse<T> {

    @Schema(description = "콘텐츠")
    private final List<T> content;

    @Schema(description = "페이지 번호(0 부터 시작)", example = "0")
    private final int page;

    @Schema(description = "한 페이지에 포함될 항목(데이터)의 개수", example = "10")
    private final int size;

    @Schema(description = "전체 데이터의 개수", example = "90")
    private final long totalElements;

    @Schema(description = "전체 페이지 수", example = "9")
    private final int totalPages;

    @Builder
    public PageResponse(List<T> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static <T, U> PageResponse<U> from(CustomPage<T> customPage, Function<? super T, ? extends U> converter) {
        List<U> convertedContent = customPage.getContent().stream()
                .map(converter)
                .collect(Collectors.toList());
        return PageResponse.<U>builder()
                .content(convertedContent)
                .page(customPage.getPage())
                .size(customPage.getSize())
                .totalElements(customPage.getTotalElements())
                .totalPages(customPage.getTotalPages())
                .build();
    }
}
