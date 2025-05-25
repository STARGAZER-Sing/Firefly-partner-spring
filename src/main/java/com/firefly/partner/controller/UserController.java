package com.firefly.partner.controller;

import com.firefly.partner.mapper.userMapper;
import com.firefly.partner.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private userMapper userMapper;

    @PostMapping("/login")
    public String login(@RequestBody User loginUser) {
        User dbUser = userMapper.getUserByName(loginUser.getName());
        if (dbUser != null && dbUser.getPassword().equals(loginUser.getPassword())) {
            return "登录成功";
        } else {
            return "用户名或密码错误";
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody User newUser) {
        User existing = userMapper.getUserByName(newUser.getName());
        if (existing != null) {
            return "用户名已存在";
        }
        userMapper.insertUsers(Collections.singletonList(newUser));
        return "注册成功";
    }

    @PostMapping("/update")
    public String update(@RequestBody User updateUser) {
        userMapper.updateUser(updateUser);
        return "更新成功";
    }

    @PostMapping("/select")
    public User select(@RequestBody String name) {
        return userMapper.getUserByName(name);
    }
}
