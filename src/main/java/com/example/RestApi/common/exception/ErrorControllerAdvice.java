package com.example.RestApi.common.exception;

import com.example.RestApi.dto.order.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(value ={CustomException.class})
    public ResponseEntity handlerCustomException(CustomException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }
}
