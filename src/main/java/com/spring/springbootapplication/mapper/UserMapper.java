package com.spring.springbootapplication.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.spring.springbootapplication.entity.User;

@Mapper
public interface UserMapper {

    @Insert("""
        INSERT INTO users (name, email, password)
        VALUES (#{name}, #{email}, #{password})
        """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("""
        SELECT COUNT(*)
        FROM users
        WHERE email = #{email}
        """)
    int countByEmail(String email);
}