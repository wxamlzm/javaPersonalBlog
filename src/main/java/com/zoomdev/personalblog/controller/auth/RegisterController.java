package com.zoomdev.personalblog.controller.auth;

import com.zoomdev.personalblog.Response;
import com.zoomdev.personalblog.model.dto.CreateUserDTO;
import com.zoomdev.personalblog.model.dto.UserDTO;
import com.zoomdev.personalblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class RegisterController extends BaseAuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response<?>> register (@Valid @RequestBody CreateUserDTO createUserDTO) {
        UserDTO createdUser = userService.createUser(createUserDTO);
        return ResponseEntity.ok(Response.newSuccess(createdUser));
    }

}
