package com.bins.gopl.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @GetMapping("/sign-up")
    public ModelAndView signup() {
        return new ModelAndView("gopl/user/signup");
    }
}