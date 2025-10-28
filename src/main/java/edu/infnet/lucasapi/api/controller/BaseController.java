package edu.infnet.lucasapi.api.controller;

import edu.infnet.lucasapi.api.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

public abstract class BaseController {

    protected <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    protected <T> ResponseEntity<ApiResponse<List<T>>> ok(Page<T> page) {
        return ResponseEntity.ok(ApiResponse.success(page));
    }

    protected <T> ResponseEntity<ApiResponse<T>> created(String path, Long id, T body) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(path + "/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity
                .created(location)
                .body(ApiResponse.success(body));
    }

    protected ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    protected <T> ResponseEntity<ApiResponse<T>> fail(List<String> messages) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(messages));
    }
}
