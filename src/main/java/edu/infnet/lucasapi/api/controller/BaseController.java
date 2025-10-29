package edu.infnet.lucasapi.api.controller;

import edu.infnet.lucasapi.api.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    protected Pageable buildPageable(Integer page, Integer size, String sort) {
        int pageNumber = (page == null || page < 0) ? 0 : page;
        int pageSize = (size == null || size <= 0) ? 10 : size;
        String sortParam = (sort == null || sort.isBlank()) ? "id,asc" : sort;

        String[] parts = sortParam.split(",");
        String field = parts[0];
        Sort.Direction direction = (parts.length > 1 && "desc".equalsIgnoreCase(parts[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
    }
}
