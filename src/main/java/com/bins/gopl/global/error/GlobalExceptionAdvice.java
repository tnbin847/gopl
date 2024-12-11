package com.bins.gopl.global.error;

import com.bins.gopl.global.common.response.ApiEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 박 수 빈
 * @version 1.0.0
 */

@RestControllerAdvice
public class GlobalExceptionAdvice {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleRequestValidException(BindException exception) {
        ErrorResponse response = ErrorResponse.fail(ApiEnum.INVALID_PARAM_VALUE, exception.getBindingResult());
        return ResponseEntity.status(ApiEnum.INVALID_PARAM_VALUE.getHttpStatus()).body(response);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception exception) {
        ErrorResponse response = ErrorResponse.error(ApiEnum.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(ApiEnum.INTERNAL_SERVER_ERROR.getHttpStatus()).body(response);
    }
}