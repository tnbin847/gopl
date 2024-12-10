package com.bins.gopl.global.common.response;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * API 응답 구조의 일관성 유지를 위해 성공 및 에러 응답 객체에서 공통적으로 포함될 속성들을 정의한 기본 응답 클래스
 *
 * @author      박 수 빈
 * @version     1.0.0
 */

@Getter
public class BaseResponse {
    /**
     * 요청에 대한 응답 일자
     */
    private final LocalDateTime responseAt;

    /**
     * 자체 정의한 응답 코드
     */
    private final int code;

    /**
     * 내부 개발자 또는 운영자를 위한 응답 메시지
     */
    private final String message;

    public BaseResponse(int code, String message) {
        this.responseAt = LocalDateTime.now();
        this.code = code;
        this.message = message;
    }
}