package com.bins.gopl.global.common.response;

import lombok.Getter;

/**
 * 성공 응답을 정의한 객체
 *
 * @author 박 수 빈
 * @version 1.0.0
 */

@Getter
public class ApiResponse<T> extends BaseResponse {
    /**
     * 요청이 정상적으로 처리될 경우 성공 응답 바디부에 담길 응답 결과 데이터로서, 해당 데이터가 존재하지 않을 경우
     * {@code null} 대신 {@code 빈 객체({})}를 담아 클라이언트에게 응답을 반환하도록 한다.
     */
    private final T data;

    private ApiResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    /**
     * 성공 응답을 반환한다.
     *
     * @param apiStatus 응답의 상태 코드와 메시지 등의 정보를 정의한 {@link Enum} 클래스
     * @param data      응답 결과 데이터
     * @return          성공 응답 객체
     * @param <T>       응답 결과 데이터의 타입
     */
    public static<T> ApiResponse<T> success(final ApiStatus apiStatus, final T data) {
        return new ApiResponse<>(apiStatus.getCode(), apiStatus.getMessage(), data);
    }
}