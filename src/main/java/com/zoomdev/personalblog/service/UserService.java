package com.zoomdev.personalblog.service;

import com.zoomdev.personalblog.model.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(long id);

}
