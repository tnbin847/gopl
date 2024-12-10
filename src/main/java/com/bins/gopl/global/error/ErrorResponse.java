package com.bins.gopl.global.error;

import com.bins.gopl.global.common.response.ApiStatus;
import com.bins.gopl.global.common.response.BaseResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 클라이언트의 잘못된 요청으로 인한 실패 응답과 예외 발생으로 인한 에러 응답 등을 정의한 객체
 *
 * @author 박 수 빈
 * @version 1.0.0
 */

@Getter
public class ErrorResponse extends BaseResponse {
    /**
     * 클라이언트의 요청에 의해 발생된 에러에 대한 정보들을 담고 있는 리스트 객체
     */
    private List<ValidationErrors> errors;

    /**
     * 에러 응답을 생성시 사용될 생성자
     */
    private ErrorResponse(int code, String message) {
        super(code, message);
    }

    /**
     * 실패 응답을 생성시 사용될 생성자
     */
    private ErrorResponse(int code, String message, List<ValidationErrors> errors) {
        super(code, message);
        this.errors = errors == null ? new ArrayList<>() : errors;
    }

    /**
     * {@link BindingResult} 기반의 실패 응답을 반환한다.
     *
     * @param apiStatus         응답의 상태 코드와 메시지 등의 정보를 정의한 {@link Enum} 클래스
     * @param bindingResult     스프링에서 제공하는 검증 실패 결과를 담고 있는 객체
     * @return                  실패 응답 객체
     */
    public static ErrorResponse fail(final ApiStatus apiStatus, final BindingResult bindingResult) {
        return new ErrorResponse(apiStatus.getCode(), apiStatus.getMessage(), ValidationErrors.of(bindingResult));
    }

    /**
     * {@link ConstraintViolation} 기반의 실패 응답을 반환한다.
     *
     * @param apiStatus                 응답의 상태 코드와 메시지 등의 정보를 정의한 {@link Enum} 클래스
     * @param constraintViolations      검증 실패 결과를 담고 있는 객체
     * @return                          실패 응답 객체
     */
    public static ErrorResponse fail(final ApiStatus apiStatus, final Set<ConstraintViolation<?>> constraintViolations) {
        return new ErrorResponse(apiStatus.getCode(), apiStatus.getMessage(), ValidationErrors.of(constraintViolations));
    }

    /**
     * 실패 응답을 반환한다.
     *
     * @param apiStatus     응답의 상태 코드와 메시지 등의 정보를 정의한 Enum 클래스
     * @param errors        에러 정보를 담고 있는 리스트
     * @return              실패 응답 객체
     */
    public static ErrorResponse fail(final ApiStatus apiStatus, final List<ValidationErrors> errors) {
        return new ErrorResponse(apiStatus.getCode(), apiStatus.getMessage(), errors);
    }

    /**
     * 예외 발생으로 인한 에러 응답 객체를 반환한다.
     *
     * @param apiStatus     응답의 상태 코드와 메시지 등의 정보를 정의한 Enum 클래스
     * @return              에러 응답 객체
     */
    public static ErrorResponse error(final ApiStatus apiStatus) {
        return new ErrorResponse(apiStatus.getCode(), apiStatus.getMessage());
    }

    /**
     * 클라이언트의 요청 파라미터의 검증 에러를 처리 후, 그 정보를 반환하는 정적 클래스
     */
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ValidationErrors {
        /**
         * 에러가 발생된 필드명
         */
        private final String field;

        /**
         * 에러가 발생된 필드에 저장된 값
         */
        private final String value;

        /**
         * 에러가 발생된 원인
         */
        private final String message;

        /**
         * 특정 필드에 의해 발생된 에러에 대한 정보를 반환한다.
         *
         * @param field     에러가 발생된 필드명
         * @param value     사용자 입력값
         * @param message   에러가 발생된 원인
         * @return          에러 정보를 담고 있는 리스트
         */
        public static List<ValidationErrors> of(final String field, final String value, final String message) {
            final List<ValidationErrors> errors = new ArrayList<>();
            errors.add(ValidationErrors.builder().field(field).value(value).message(message).build());
            return errors;
        }

        /**
         * 요청 파라미터의 유효성 검증 실패로 인해 발생된 에러에 대한 정보를 반환한다.
         * <p> 주로 {@code @Valid}와 함께 {@code @ResponseBody} 또는 {@code @ModelAttribute}를 함께 사용했을 때 발생되는
         * {@link org.springframework.web.bind.MethodArgumentNotValidException} 또는
         * {@link org.springframework.validation.BindException}을 처리할 때 호출된다.</p>
         *
         * @param bindingResult     스프링에서 제공하는 폼 데이터 바인딩 과정 중에 발생되는 검증 오류를 수집 및 처리하는 객체
         * @return                  에러 정보를 담고 있는 리스트
         */
        public static List<ValidationErrors> of(final BindingResult bindingResult) {
            final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream().map(fieldError -> ValidationErrors.builder()
                    .field(fieldError.getField())
                    .value(nullSafeToString(fieldError.getRejectedValue()))
                    .message(fieldError.getDefaultMessage())
                    .build()).collect(Collectors.toList());
        }

        /**
         * 요청 파라미터의 유효성 검증 실패로 인해 발생된 에러에 대한 정보를 반환한다.
         * <p> 주로 {@code @Validated} 사용함으로 인해 파라미터가 제약 조건 위반으로 인해 발생된
         * {@link javax.validation.ConstraintViolationException}을 처리할 때 호출된다.</p>
         *
         * @param violations    검증 실패 원인에 대한 정보를 담고 있는 객체
         * @return              에러 정보를 담고 있는 리스트
         */
        public static List<ValidationErrors> of(final Set<ConstraintViolation<?>> violations) {
            final List<ConstraintViolation<?>> constraintViolations = new ArrayList<>(violations);
            return constraintViolations.stream().map(constraintViolation -> ValidationErrors.builder()
                    .field(parsingPropertyName(constraintViolation.getPropertyPath().toString()))
                    .value(nullSafeToString(constraintViolation.getInvalidValue()))
                    .message(constraintViolation.getMessage())
                    .build()).collect(Collectors.toList());
        }

        /**
         * 인자로 전달받은 값을 문자열로 변환후 반환한다. 이때 인자값이 {@code null}일 경우, 빈 문자열을 반환한다.
         *
         * @param object    에러가 발생된 필드에 저장된 값
         * @return          문자열로 변환된 값 또는 빈 문자열
         */
        private static String nullSafeToString(final Object object) {
            return object == null ? "" : object.toString();
        }

        /**
         * 에러가 발생된 속성의 경로에서 '.' 기준으로 속성명을 추출 및 반환하되, 경로에 '.'이 없을 경우 경로 그 자체를 반환한다.
         *
         * @param propertyPath  유효성 검증 에러가 발생된 속성 경로
         * @return              속성 명 또는 속성의 경로
         */
        private static String parsingPropertyName(final String propertyPath) {
            final int lastIndex = propertyPath.lastIndexOf('.');
            return lastIndex == -1 ? propertyPath : propertyPath.substring(lastIndex);
        }
    }
}