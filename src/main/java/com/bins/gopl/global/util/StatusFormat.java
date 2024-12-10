package com.bins.gopl.global.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 사용 여부 또는 삭제 여부 등의 특정 상태에 대한 값들을 코드단에서 일관적으로 처리하기 위해
 * 서로 다른 타입의 값들을 상응되는 의미 별로 묶어 정의한 {@link Enum} 클래스
 *
 * @author 박 수 빈
 * @version 1.0.0
 */

@RequiredArgsConstructor
@Getter
public enum StatusFormat {
    YES (1, "Y", true),
    NO (0, "N", false);

    /**
     * 정수형 상태 값
     */
   private final int number;

    /**
     * 문자열 타입의 상태 값
     */
   private final String symbol;

    /**
     * 논리형 상태 값
     */
   private final boolean bool;

    /**
     * 인자로 전달 받은 정수형의 값에 해당되는 논리형의 상태 값을 반환한다.
     */
   public static boolean numberToBoolean(int num) {
       return Arrays.stream(values())
               .filter(format -> format.getNumber() == num)
               .findFirst()
               .map(StatusFormat::isBool)
               .orElseThrow(() -> new IllegalArgumentException("Cannot convert '" + num + "' boolean type."));
   }
}