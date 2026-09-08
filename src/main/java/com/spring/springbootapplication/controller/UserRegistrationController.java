package com.spring.springbootapplication.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.springbootapplication.dto.UserRegistrationRequest;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.service.UserService;

@Controller
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute(
            "userRegistrationRequest",
            new UserRegistrationRequest()
        );
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute
            UserRegistrationRequest userRegistrationRequest,
            BindingResult bindingResult,
            HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.existsByEmail(
                userRegistrationRequest.getEmail())) {

            bindingResult.addError(
                new FieldError(
                    "userRegistrationRequest",
                    "email",
                    "このメールアドレスは既に登録されています"
                )
            );

            return "register";
        }

        User user = userService.register(
            userRegistrationRequest
        );

        session.setAttribute("loginUser", user);

        return "redirect:/";
    }
}