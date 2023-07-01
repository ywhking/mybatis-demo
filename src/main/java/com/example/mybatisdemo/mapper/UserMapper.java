package com.example.mybatisdemo.mapper;

import java.util.List;

import com.example.mybatisdemo.model.User;

public interface UserMapper{
    List<User> getAllUser();
    User getUser(Long id);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
}