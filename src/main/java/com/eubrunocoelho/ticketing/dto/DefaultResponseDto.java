package com.eubrunocoelho.ticketing.dto;

public interface DefaultResponseDto<T> {

    String label();

    T data();
}
