package com.spring.springbootapplication.service;

import java.io.IOException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.springbootapplication.dto.UserRegistrationRequest;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.mapper.UserMapper;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean existsByEmail(String email) {
        return userMapper.countByEmail(email) > 0;
    }

    public User register(UserRegistrationRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userMapper.insert(user);

        return user;
    }

    public User login(String email, String password) {

        User user = userMapper.findByEmail(email);

        if (user == null) {
            return null;
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        return user;
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    public User updateProfile(
            Long id,
            String introduction,
            MultipartFile profileImage) throws IOException {

        if (profileImage == null || profileImage.isEmpty()) {
            userMapper.updateIntroduction(
                id,
                introduction
            );
        } else {
            userMapper.updateProfile(
                id,
                introduction,
                profileImage.getBytes(),
                profileImage.getContentType()
            );
        }

        return userMapper.findById(id);
    }
}
