package edu.infnet.lucasapi.api.response;

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
public class ApiResponse<T> {
    private boolean success;
    private Long count;
    private Integer page;
    private Integer totalPages;
    private Integer size;
    private String message;
    private T data;
    private List<String> messages;

    public static <T> ApiResponse<T> success(T data) {
        Long count = (data instanceof Collection<?>)
                ? (long) ((Collection<?>) data).size()
                : null;

        return ApiResponse.<T>builder()
                .success(true)
                .count(count)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<List<T>> success(Page<T> page) {
        long count = page.getNumberOfElements();

        var builder = ApiResponse.<List<T>>builder()
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

    public static <T> ApiResponse<T> fail(List<String> messages) {
        return ApiResponse.<T>builder()
                .success(false)
                .messages(messages)
                .build();
    }

    public static <T> ApiResponse<T> fail(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
}
