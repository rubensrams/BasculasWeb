package com.materiales.bascula.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "pages/home";
    }

    @GetMapping("/login.html")
    public String login() {
        return "login";
    }

    
}
