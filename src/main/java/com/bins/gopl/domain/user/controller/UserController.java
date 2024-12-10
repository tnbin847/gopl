package com.bins.gopl.domain.user.controller;

import com.bins.gopl.domain.user.dto.SignUpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 박 수 빈
 * @version 1.0.0
 */

@RestController
public class UserController {

    @GetMapping("/sign-up")
    public ModelAndView signUp() {
        return new ModelAndView("gopl/user/signup");
    }

    @PostMapping("/api/v1/user/account/exists/id/{id}")
    public void findExistsById(@PathVariable("id") final String id) {
    }

    @PostMapping("/api/v1/user/account/exists/email/{email}")
    public void findExistsByEmail(@PathVariable("email") final String email) {
    }

    @PostMapping("/api/v1/user/account")
    public void createUser(@RequestBody final SignUpRequest signUpRequest) {
    }
}