package com.bins.gopl.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 박 수 빈
 * @version 1.0.0
 */

@Controller
public class LoginController {

    @GetMapping("/")
    public String login() {
        return "gopl/index";
    }
}