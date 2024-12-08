package com.bins.gopl.global.common.mybatis;


/**
 * 데이터베이스 처리를 하고자 하는 {@link Enum}클래스를 일관적으로 구현하기 위해 정의한 인터페이스
 *
 * @author 박 수 빈
 * @version 1.0.0
 */

public interface CodeEnum {
    /**
     * 데이터베이스에 저장할 {@link Enum} 클래스 내에 정의된 문자열 타입의 코드값을 반환한다.
     */
    String getCode();

    /**
     * 뷰에 출력할 코드값에 대한 라벨 값을 반환한다.
     */
    String getLabel();
}