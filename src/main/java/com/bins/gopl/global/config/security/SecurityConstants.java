package com.bins.gopl.global.config.security;

/**
 * 스프링 시큐리티 처리를 위해 관련 상수들을 정의한 상수 클래스
 *
 * @author 박 수 빈
 * @version 1.0.0
 */

public final class SecurityConstants {
    /**
     * 인증을 요구하지 않고 접근을 허용할 요청 URL들을 정의한 상수 배열
     */
    public static final String[] PUBLICY_URL_MATCHERS = {
            /* 페이지 요청 URL */
            "/",
            "/sign-up",
            "/find-id",
            "/find-pwd",
            /* API 요청 URL */
            "/api/v1/login",
            "/api/v1/logout",
            "/api/v1/user/account/**",
            "/api/v1/user/account"
    };

    /**
     * JSON 형식으로 일반 로그인 처리시 필터에서 사용될 접근주체 파라미터
     */
    public static final String PRINCIPAL_KEY = "id";

    /**
     * JSON 형식으로 일반 로그인 처리시 필터에서 사용될 비밀번호 파라미터
     */
    public static final String CREDENTIAL_KEY = "password";
}