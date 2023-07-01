package com.example.mybatisdemo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mybatisdemo.model.User;
import com.example.mybatisdemo.mapper.UserMapper;

@RestController
public class UserController{
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user")
    public List<User> getUser(@RequestParam(name="id",required=false) Long id){
        System.out.printf("get user by id:%d", id);
        if(id != null){
            System.out.printf("get user by id:%d", id);
            User user = userMapper.getUser(id);
            List<User> users = new ArrayList<User>();
            users.add(user);
            return users;
        }else{
            System.out.println("get all user");
            List<User> users = userMapper.getAllUser();
            return users;            
        }
    }

    @PostMapping("/user")
    public User addUser(
        @RequestParam(name="name") String name,
        @RequestParam(name="email",required=false) String email,
        @RequestParam(name="phone_number",required=false) String phoneNumber,
        @RequestParam(name="password") String password){

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        
        userMapper.addUser(user);
        return user;            
    }

    @PutMapping("/user")
    public User updateUser(
        @RequestParam(name="id") Long id,
        @RequestParam(name="name",required=false) String name,
        @RequestParam(name="email",required=false) String email,
        @RequestParam(name="phone_number",required=false) String phoneNumber,
        @RequestParam(name="password",required=false) String password){
        
        User user = userMapper.getUser(id);
        if(user != null){
            if(name != null)
                user.setName(name);
            if(email != null)
                user.setEmail(email);
            if(phoneNumber != null)
                user.setPhoneNumber(phoneNumber);
            if(password != null)
                user.setPassword(password);
            userMapper.updateUser(user);
            return user;
        }else{
            return null;            
        }
    }

    @DeleteMapping("/user")
    public Long deleteUser(@RequestParam(name="id") Long id){
        userMapper.deleteUser(id);
        return id;            
    }
}