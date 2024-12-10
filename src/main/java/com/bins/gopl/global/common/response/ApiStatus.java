package com.bins.gopl.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 성공 및 에러 응답에 대한 코드, 메시지 등의 정보를 정의한 {@link Enum}클래스
 *
 * @author 박 수 빈
 * @version 1.0.0
 */

@Getter
@RequiredArgsConstructor
public enum ApiStatus {
    /**
     * <p>성공 응답 코드</p>
     * <p>{@code code} 구성</p>
     * <ul>
     * <li><b>첫번째 ~ 세번째 자리</b> : HTTP 상태 코드 - 100</li>
     * <li><b>네번째 자리</b> : 요청 타입 코드 → OK : 0, CREATE : 1, UPDATE : 2, DELETE : 3</li>
     * <li><b>다섯번째 ~ 여섯번째 자리</b> : 구분 코드 (도메인 순번 + 도메인 내 요청 대상이 되는 파라미터의 위치 순번 → 없다면 0 또는 0 부터 임의의 숫자)</li>
     * </ul>
     */
    /* 공통 */
    OK (100000, HttpStatus.OK, "요청이 정상적으로 처리되었습니다."),
    CREATE (101010, HttpStatus.CREATED, "리소스가 생성되었습니다."),
    UPDATE (104020, HttpStatus.NO_CONTENT, "리소스가 변경되었습니다."),
    DELETE (104030, HttpStatus.NO_CONTENT, "리소스가 삭제되었습니다."),

    /**
     * 에러 응답 코드
     * <p>{@code code} 구성</p>
     * <ul>
     * <li><b>첫번째 ~ 세번째 자리</b> : -HTTP 상태 코드 + 400</li>
     * <li><b>네번째 자리</b> : 에러 타입 코드 → 서버 : 0, 파라미터 : 1, 리소스 : 2, 인증 : 3</li>
     * <li><b>다섯번째 ~ 여섯번째 자리</b> : 구분 코드 (도메인 순번 + 도메인 내 에러 원인이 되는 파라미터의 위치 순번 → 없다면 0 또는 0 부터 임의의 숫자)</li>
     * </ul>
     */
    
    /* 공통 */
    INVALID_PARAM_VALUE (-800010, HttpStatus.BAD_REQUEST, "요청 파라미터의 값이 유효하지 않습니다."),
    INVALID_PARAM_TYPE (-800014, HttpStatus.BAD_REQUEST, "요청 파라미터의 타입이 올바르지 않습니다."),
    NOT_FOUND_RESOURCE (-804, HttpStatus.NOT_FOUND, "요청하신 자원을 찾을 수 없습니다."),
    NOT_SUPPORTED_METHOD (-805, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메소드 방식의 요청입니다."),
    INTERNAL_SERVER_ERROR (-900, HttpStatus.INTERNAL_SERVER_ERROR, "내부 에러가 발생했습니다.")
    ;

    /**
     * 요청 처리 결과 상태를 의미하는 응답 코드
     */
    private final int code;

    /**
     * HTTP 상태 코드
     */
    private final HttpStatus httpStatus;

    /**
     * 내부 개발자 또는 운영자를 위한 응답 메시지
     */
    private final String message;
}