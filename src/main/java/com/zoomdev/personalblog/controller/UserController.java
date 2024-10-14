package com.zoomdev.personalblog.controller;

import com.zoomdev.personalblog.Response;
import com.zoomdev.personalblog.model.dto.CreateUserDTO;
import com.zoomdev.personalblog.model.dto.UserDTO;
import com.zoomdev.personalblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Response<UserDTO> getUserById(@PathVariable long id) {
        return Response.newSuccess(userService.getUserById(id));
    }

    @PostMapping
    public Response createUser(@RequestBody CreateUserDTO createUserDTO){
        UserDTO createdUser = userService.createUser(createUserDTO);
        return Response.newSuccess(createdUser);
    }
}
