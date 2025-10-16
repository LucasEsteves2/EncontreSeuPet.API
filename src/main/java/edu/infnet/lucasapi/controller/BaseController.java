package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.controller.dto.ApiResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

public abstract class BaseController {

    protected <T> ResponseEntity<ApiResponseDto<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponseDto.success(data));
    }

    protected <T> ResponseEntity<ApiResponseDto<List<T>>> ok(Page<T> page) {
        return ResponseEntity.ok(ApiResponseDto.success(page));
    }

    protected <T> ResponseEntity<ApiResponseDto<T>> created(String path, Long id, T body) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(path + "/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity
                .created(location)
                .body(ApiResponseDto.success(body));
    }

    protected ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    protected <T> ResponseEntity<ApiResponseDto<T>> fail(String message) {
        return ResponseEntity.badRequest().body(ApiResponseDto.fail(message));
    }
}