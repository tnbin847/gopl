package com.bins.gopl.domain.user.service;

import com.bins.gopl.domain.user.dto.SignUpRequest;
import com.bins.gopl.domain.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 박 수 빈
 * @version 1.0.0
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void addUser(final SignUpRequest signUpReq) {
    }
}