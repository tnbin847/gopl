package com.bins.gopl.domain.user.controller;

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
}