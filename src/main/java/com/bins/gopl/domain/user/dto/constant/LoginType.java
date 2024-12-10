package com.bins.gopl.domain.user.dto.constant;

import com.bins.gopl.global.common.mybatis.CodeEnum;
import lombok.RequiredArgsConstructor;

/**
 * @author 박 수 빈
 * @version 1.0.0
 */

@RequiredArgsConstructor
public enum LoginType implements CodeEnum {
    BASIC ("LOCAL", "로컬"),
    SOCIAL ("OAUTH", "소셜");

    private final String code;
    private final String label;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}