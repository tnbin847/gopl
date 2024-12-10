package com.bins.gopl.domain.user.dto;

import com.bins.gopl.domain.user.dto.constant.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 박 수 빈
 * @version 1.0.0
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserRoleRequest {
    private Long userId;

    private Role role;

    private String useYn;

    private String deleteYn;

    @Builder
    public UserRoleRequest(Long userId, Role role, String useYn, String deleteYn) {
        this.userId = userId;
        this.role = role;
        this.useYn = useYn;
        this.deleteYn = deleteYn;
    }
}