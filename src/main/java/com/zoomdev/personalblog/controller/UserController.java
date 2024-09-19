package com.zoomdev.personalblog.controller;

import com.zoomdev.personalblog.model.entity.User;
import com.zoomdev.personalblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }
}
