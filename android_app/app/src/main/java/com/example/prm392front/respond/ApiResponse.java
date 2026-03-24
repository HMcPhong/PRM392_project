package com.example.prm392front.respond;

import com.example.prm392front.model.Product;

import java.util.List;

public class ApiResponse<T> {
    private String message;
    private T data;

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}