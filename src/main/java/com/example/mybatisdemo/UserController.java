package com.example.mybatisdemo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mybatisdemo.model.User;
//import com.example.mybatisdemo.dao.UserRepository;
import com.example.mybatisdemo.mapper.UserMapper;

@RestController
public class UserController{
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user")
    public List<User> getUser(@RequestParam(name="id",required=false) Long id){
        if(id != null){
            User user = userMapper.findUserById(id);
            List<User> users = new ArrayList<User>();
            users.add(user);
            return users;
        }else{
            List<User> users = userMapper.findAll();
            return users;            
        }
    }
}