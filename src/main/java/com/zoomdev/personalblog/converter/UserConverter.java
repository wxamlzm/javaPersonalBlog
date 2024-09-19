package com.zoomdev.personalblog.converter;

import com.zoomdev.personalblog.model.dto.UserDTO;
import com.zoomdev.personalblog.model.entity.User;

public class UserConverter {
    public static UserDTO convertUser(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getName());
        return userDTO;
    }
}
