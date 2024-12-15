package com.ureca.gate.global.dto.response;

import com.ureca.gate.global.domain.CustomSlice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class SliceResponse<T> {

    @Schema(description = "콘텐츠")
    private final List<T> content;

    @Schema(description = "페이지 번호(0 부터 시작)", example = "0")
    private final int page;

    @Schema(description = "한 페이지에 포함될 항목(데이터)의 개수", example = "10")
    private final int size;

    @Schema(description = "다음 페이지 존재 여부", example = "true")
    private final boolean hasNext;

    @Builder
    public SliceResponse(List<T> content, int page, int size, boolean hasNext) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
    }

    public static <T, U> SliceResponse<U> from(CustomSlice<T> customSlice, Function<? super T, ? extends U> converter) {
        List<U> convertedContent = customSlice.getContent().stream()
                .map(converter)
                .collect(Collectors.toList());
        return SliceResponse.<U>builder()
                .content(convertedContent)
                .page(customSlice.getPage())
                .size(customSlice.getSize())
                .hasNext(customSlice.isHasNext())
                .build();
    }
}
