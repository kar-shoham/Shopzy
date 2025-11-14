package com.shopzy.user_service.controller;

import com.shopzy.user_service.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRTE(RuntimeException ex)
    {
        return ResponseEntity.internalServerError()
                .body(ErrorResponseDto.builder()
                        .message(ex.getMessage())
                        .stackTrace(Arrays.toString(ex.getStackTrace()))
                        .build());
    }
}
