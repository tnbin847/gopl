package com.bins.gopl.global.common.mybatis;


/**
 * 데이터베이스 저장하고자 하는 코드값을 정의한 {@link Enum}클래스를 일관적으로 구현하기 위한 인터페이스
 */

public interface CodeEnum {
    /**
     * 데이터베이스 저장 또는 데이터베이스로부터 가져온 코드를 알맞은 {@link Enum}으로 변환하기 위한 {@code code}값을 가져온다.
     */
    String getCode();

    /**
     * 뷰에 출력할 코드명을 가져온다.
     */
    String getDescription();
}