package com.spring.springbootapplication.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.springbootapplication.dto.LoginRequest;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.service.UserService;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute(
            "loginRequest",
            new LoginRequest()
        );
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute LoginRequest loginRequest,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = userService.login(
            loginRequest.getEmail(),
            loginRequest.getPassword()
        );

        if (user == null) {
            redirectAttributes.addFlashAttribute(
                "errorMessage",
                "メールアドレス、もしくはパスワードが間違っています"
            );

            return "redirect:/login";
        }

        session.setAttribute("loginUser", user);

        return "redirect:/";
    }
}