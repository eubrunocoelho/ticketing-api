package com.eubrunocoelho.ticketing.dto.response;

public record ResponseDto<T>(

        String label,

        T data
) implements ResponseInterfaceDto<T> {
}
