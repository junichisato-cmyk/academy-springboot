package com.spring.springbootapplication.entity;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String introduction;

    private byte[] profileImage;

    private String profileImageContentType;
}
