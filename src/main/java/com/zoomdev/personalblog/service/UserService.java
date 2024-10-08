package com.zoomdev.personalblog.service;

import com.zoomdev.personalblog.model.dto.CreateUserDTO;
import com.zoomdev.personalblog.model.dto.UserDTO;
import com.zoomdev.personalblog.model.entity.User;

public interface UserService {
    UserDTO getUserById(long id);

    UserDTO getUserByUsername(String username);

    UserDTO createUser(CreateUserDTO createUserDTO);
}
