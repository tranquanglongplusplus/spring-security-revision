package com.spring.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String getHome(Authentication authentication, Model model) {
        model.addAttribute("user", authentication.getName());
        return "home";
    }
}
