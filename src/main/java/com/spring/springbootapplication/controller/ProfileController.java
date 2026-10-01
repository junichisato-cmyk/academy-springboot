package com.spring.springbootapplication.controller;

import java.io.IOException;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.springbootapplication.dto.ProfileEditRequest;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.service.UserService;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/edit")
    public String showEditForm(
            HttpSession session,
            Model model) {

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        ProfileEditRequest profileEditRequest =
            new ProfileEditRequest();

        profileEditRequest.setIntroduction(
            loginUser.getIntroduction()
        );

        model.addAttribute(
            "profileEditRequest",
            profileEditRequest
        );

        return "profile-edit";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(
            @Valid
            @ModelAttribute("profileEditRequest")
            ProfileEditRequest profileEditRequest,
            BindingResult bindingResult,
            @RequestParam("profileImage")
            MultipartFile profileImage,
            HttpSession session) throws IOException {

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "profile-edit";
        }

        User updatedUser = userService.updateProfile(
            loginUser.getId(),
            profileEditRequest.getIntroduction(),
            profileImage
        );

        session.setAttribute(
            "loginUser",
            updatedUser
        );

        return "redirect:/";
    }

    @GetMapping("/profile/image")
    public ResponseEntity<byte[]> showProfileImage(
            HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return ResponseEntity.notFound().build();
        }

        User user = userService.findById(loginUser.getId());

        if (user == null || user.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        if (user.getProfileImageContentType() != null) {
            mediaType = MediaType.parseMediaType(
                user.getProfileImageContentType()
            );
        }

        return ResponseEntity
            .ok()
            .header(
                HttpHeaders.CONTENT_TYPE,
                mediaType.toString()
            )
            .body(user.getProfileImage());
    }
}