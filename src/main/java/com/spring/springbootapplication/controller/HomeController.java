package com.spring.springbootapplication.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.springbootapplication.entity.User;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        return "index";
    }
}