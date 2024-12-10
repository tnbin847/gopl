package com.bins.gopl.domain.user.mapper;

import com.bins.gopl.domain.user.dto.SignUpRequest;
import com.bins.gopl.domain.user.dto.UserRoleRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 박 수 빈
 * @version 1.0.0
 */

@Mapper
public interface UserMapper {
    /**
     * 요청한 아이디의 존재여부를 확인한다.
     */
    boolean existsById(String id);

    /**
     * 요청한 이메일의 존재여부를 확인한다.
     */
    boolean existsByEmail(String email);

    /**
     * 회원가입 정보를 저장한다.
     */
    int insertUser(SignUpRequest signUpRequest);

    /**
     * 사용자 계정의 새로운 권한 정보를 등록한다.
     */
    void insertUserRole(UserRoleRequest userRoleRequest);
}