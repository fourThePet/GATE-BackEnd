package com.ureca.gate.global.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Builder
public class CustomSlice<T>  {

    private final List<T> content;
    private final int page;
    private final int size;
    private final boolean hasNext;

    // 변환 메소드: 페이지 콘텐츠를 다른 타입으로 변환
    public static <T, U> CustomSlice<U> convert(CustomSlice<T> sourcePage, Function<? super T, ? extends U> converter) {
        List<U> convertedContent = sourcePage.getContent()
                .stream()
                .map(converter)
                .collect(Collectors.toList());
        return CustomSlice.<U>builder()
                .content(convertedContent)
                .page(sourcePage.getPage())
                .size(sourcePage.getSize())
                .hasNext(sourcePage.hasNext)
                .build();
    }

    // Slice에서 CustomPage로 변환
    public static <T> CustomSlice<T> from(Slice<T> slice) {
        return CustomSlice.<T>builder()
                .content(slice.getContent())
                .page(slice.getNumber())
                .size(slice.getSize())
                .hasNext(slice.hasNext())  // hasNext()로 다음 페이지 여부를 설정
                .build();
    }
}
