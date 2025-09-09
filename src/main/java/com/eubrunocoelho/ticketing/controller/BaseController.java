package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.util.ResponseBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

public abstract class BaseController {

    protected <T> ResponseEntity<ResponseDto<T>> createdResponse(T data, Object pathVariable) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pathVariable)
                .toUri();

        return ResponseEntity.created(location).body(ResponseBuilder.buildSingle(data));
    }

    protected <T> ResponseEntity<ResponseDto<T>> okResponse(T data) {
        return ResponseEntity.ok(ResponseBuilder.buildSingle(data));
    }

    protected <T> ResponseEntity<ResponseDto<List<T>>> okResponse(Page<T> page) {
        return ResponseEntity.ok(ResponseBuilder.buildPaged(page));
    }

    protected ResponseEntity<Void> noContentResponse() {
        return ResponseEntity.noContent().build();
    }
}
