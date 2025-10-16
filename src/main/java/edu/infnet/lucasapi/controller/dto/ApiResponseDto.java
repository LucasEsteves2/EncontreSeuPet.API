package edu.infnet.lucasapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto<T> {
    private boolean success;
    private Long count;
    private Integer page;
    private Integer totalPages;
    private Integer size;
    private T data;
    private String message;
    private List<String> messages;

    public static <T> ApiResponseDto<T> success(T data) {
        Long count = (data instanceof Collection<?>)
                ? (long) ((Collection<?>) data).size()
                : null;

        return ApiResponseDto.<T>builder()
                .success(true)
                .count(count)
                .data(data)
                .build();
    }

    public static <T> ApiResponseDto<List<T>> success(Page<T> page) {
        return ApiResponseDto.<List<T>>builder()
                .success(true)
                .page(page.getNumber())
                .totalPages(page.getTotalPages())
                .size(page.getSize())
                .count((long) page.getNumberOfElements())
                .data(page.getContent())
                .build();
    }

    public static <T> ApiResponseDto<T> fail(String message) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .message(message)
                .build();
    }

    public static <T> ApiResponseDto<T> fail(List<String> messages) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .messages(messages)
                .build();
    }
}
