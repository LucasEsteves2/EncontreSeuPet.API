package edu.infnet.lucasapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private long count;
    private T data;
    private String message;

    public static <T> ApiResponse<T> success(T data) {
        long count = 0;

        if (data instanceof Collection<?>) {
            count = ((Collection<?>) data).size();
        }

        return new ApiResponse<>(true, count, data, null);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, 0, null, message);
    }
}