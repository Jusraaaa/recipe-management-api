package com.example.demo.exception;

public class CategoryNotFoundExeption extends RuntimeException {
    public CategoryNotFoundExeption(String message) {
        super(message);
    }
}
