package com.example.SmartPark.utils;

import com.example.SmartPark.dto.response.Response;

public class ResponseUtil {

    //Success with Data
    public static <T> Response<T> success(T data, String message, int statusCode) {
        return Response.<T>builder()
                .success(true)
                .statusCode(statusCode)
                .message(message)
                .data(data)
                .build();
    }

    //Success without Data
    public static Response<Void> success(String message, int statusCode) {
        return Response.<Void>builder()
                .success(true)
                .statusCode(statusCode)
                .message(message)
                .data(null)
                .build();
    }

    // Error
    public static Response<Void> error(String message, int statusCode) {
        return Response.<Void>builder()
                .success(false)
                .statusCode(statusCode)
                .message(message)
                .data(null)
                .build();
    }
}
