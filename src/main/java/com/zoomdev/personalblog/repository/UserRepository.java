package com.zoomdev.personalblog.repository;

import com.zoomdev.personalblog.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
