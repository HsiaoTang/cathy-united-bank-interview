package com.cub.interview.cathyunitedbankinterview.handler;

import com.cub.interview.cathyunitedbankinterview.bean.dto.response.DefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultResponse<Object>> handleGeneralException(Exception e) {
        DefaultResponse<Object> response = DefaultResponse.fail(String.format("server error: %s", e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
