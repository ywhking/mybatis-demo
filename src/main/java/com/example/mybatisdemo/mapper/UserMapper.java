package com.example.mybatisdemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.example.mybatisdemo.model.User;

@Mapper
public interface UserMapper{
    @Select("SELECT * FROM user")
    List<User> findAll();

    @Select("SELECT * FROM user WHERE id=#{id}")
    User findUserById(Long id);
}