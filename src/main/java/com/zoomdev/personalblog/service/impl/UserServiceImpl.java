package com.zoomdev.personalblog.service.impl;

import com.zoomdev.personalblog.converter.UserConverter;
import com.zoomdev.personalblog.model.dto.CreateUserDTO;
import com.zoomdev.personalblog.model.dto.UserDTO;
import com.zoomdev.personalblog.model.entity.User;
import com.zoomdev.personalblog.repository.UserRepository;
import com.zoomdev.personalblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return UserConverter.convertUser(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found width username: " + username);
        }
        return UserConverter.convertUser(user);
    }

    @Override
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        if (!createUserDTO.getPassword().equals(createUserDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password do not match");
        }

        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        // user.setEmail(createUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return UserConverter.convertUser(savedUser);
    }
}
