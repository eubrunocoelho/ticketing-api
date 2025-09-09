package com.eubrunocoelho.ticketing.util;

import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.meta.MetaResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class ResponseBuilder {

    public static <T> ResponseDto<T> buildSingle(T data) {
        String screenLabel = ScreenLabelContext.getLabel();

        return new ResponseDto<>(screenLabel, data, null);
    }

    public static <T> ResponseDto<List<T>> buildPaged(Page<T> page) {
        String screenLabel = ScreenLabelContext.getLabel();

        MetaResponseDto meta = new MetaResponseDto(
                page.isFirst(),
                page.isLast(),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );

        return new ResponseDto<>(screenLabel, page.getContent(), meta);
    }
}
