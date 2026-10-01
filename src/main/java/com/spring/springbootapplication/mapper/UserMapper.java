package com.spring.springbootapplication.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select("""
        SELECT id,
               name,
               email,
               password,
               introduction,
               profile_image AS profileImage,
               profile_image_content_type AS profileImageContentType
        FROM users
        WHERE email = #{email}
        """)
    User findByEmail(String email);

    @Select("""
        SELECT id,
               name,
               email,
               password,
               introduction,
               profile_image AS profileImage,
               profile_image_content_type AS profileImageContentType
        FROM users
        WHERE id = #{id}
        """)
    User findById(Long id);

    @Update("""
        UPDATE users
        SET introduction = #{introduction},
            updated_at = CURRENT_TIMESTAMP
        WHERE id = #{id}
        """)
    void updateIntroduction(
        Long id,
        String introduction
    );

    @Update("""
        UPDATE users
        SET introduction = #{introduction},
            profile_image = #{profileImage},
            profile_image_content_type = #{profileImageContentType},
            updated_at = CURRENT_TIMESTAMP
        WHERE id = #{id}
        """)
    void updateProfile(
        Long id,
        String introduction,
        byte[] profileImage,
        String profileImageContentType
    );
}
