package edu.infnet.lucasapi.controller.dto.response;

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
    private String errorMessage;
    private T data;
    private List<String> errorMessages;

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
        long count = page.getNumberOfElements();

        var builder = ApiResponseDto.<List<T>>builder()
                .success(true)
                .count(count)
                .data(page.getContent());

        if (count > 0) {
            builder.page(page.getNumber())
                    .totalPages(page.getTotalPages())
                    .size(page.getSize());
        }

        return builder.build();
    }

    public static <T> ApiResponseDto<T> fail(List<String> messages) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .errorMessages(messages)
                .build();
    }

    public static <T> ApiResponseDto<T> fail(String message) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .errorMessage(message)
                .build();
    }
}
