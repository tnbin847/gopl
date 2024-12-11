package com.bins.gopl.global.error;

import com.bins.gopl.global.common.response.ApiEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 전역 예외 처리 클래스
 *
 * @author 박 수 빈
 * @version 1.0.0
 */

@RestControllerAdvice
public class GlobalExceptionAdvisor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException exception) {
        final ErrorResponse response = ErrorResponse.fail(ApiEnum.INVALID_PARAM_VALUE, exception.getBindingResult());
        return ResponseEntity
                .status(ApiEnum.INVALID_PARAM_VALUE.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        final ErrorResponse response = ErrorResponse.fail(ApiEnum.INVALID_PARAM_VALUE, exception.getConstraintViolations());
        return ResponseEntity
                .status(ApiEnum.INVALID_PARAM_VALUE.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        final String value = ErrorResponse.ValidationErrors.nullSafeToString(exception.getValue());
        final String message = "'" + exception.getName() + "'은 '" + exception.getRequiredType().getName() + "' 타입이어야 합니다.";
        final List<ErrorResponse.ValidationErrors> errors = ErrorResponse.ValidationErrors.of(exception.getName(), value, message);
        return ResponseEntity
                .status(ApiEnum.INVALID_PARAM_TYPE.getHttpStatus())
                .body(ErrorResponse.fail(ApiEnum.INVALID_PARAM_TYPE, errors));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception exception) {
        final ErrorResponse response = ErrorResponse.error(ApiEnum.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(ApiEnum.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(response);
    }
}