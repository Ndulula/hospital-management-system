package com.hospital.management.controller;

import com.hospital.management.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    
    protected <T> ResponseEntity<ApiResponse<T>> successResponse(String message, T data) {
        return ResponseEntity.ok(new ApiResponse<>(true, message, data));
    }
    
    protected <T> ResponseEntity<ApiResponse<T>> createdResponse(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, message, data));
    }
    
    protected <T> ResponseEntity<ApiResponse<T>> errorResponse(String message) {
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, message, null));
    }
    
    protected ResponseEntity<Void> noContentResponse() {
        return ResponseEntity.noContent().build();
    }
}