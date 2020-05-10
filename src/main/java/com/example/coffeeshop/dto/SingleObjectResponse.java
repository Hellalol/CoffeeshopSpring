package com.example.coffeeshop.dto;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
public final class SingleObjectResponse<T> {
    private final boolean success;
    private final String message;
    private final T payload;

    public static <U> SingleObjectResponse<U> success(String message, U payload) {
        return new SingleObjectResponse<>(true, message, payload);
    }

    public static <U> SingleObjectResponse<U> fail(String message) {
        return new SingleObjectResponse<>(false, message, null);
    }
}
