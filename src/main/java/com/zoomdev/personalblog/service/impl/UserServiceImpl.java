package com.zoomdev.personalblog.service.impl;

import com.zoomdev.personalblog.converter.UserConverter;
import com.zoomdev.personalblog.model.dto.UserDTO;
import com.zoomdev.personalblog.model.entity.User;
import com.zoomdev.personalblog.repository.UserRepository;
import com.zoomdev.personalblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return UserConverter.convertUser(user);
    }

    @Override
    public UserDTO getUserByUsername(String username){
        User user = userRepository.findByUsername(username);
            if(user == null){
                throw new RuntimeException("User not found width username: " + username);
            }
            return UserConverter.convertUser(user);
        }
}
