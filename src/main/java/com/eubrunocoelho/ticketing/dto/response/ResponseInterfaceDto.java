package com.eubrunocoelho.ticketing.dto.response;

public interface ResponseInterfaceDto<T> {

    String label();

    T data();
}
