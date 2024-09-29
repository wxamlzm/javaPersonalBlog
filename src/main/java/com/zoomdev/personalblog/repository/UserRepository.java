package com.zoomdev.personalblog.repository;

import com.zoomdev.personalblog.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 这些方法需要声明，因为他们是基于方法名的查询
    User findByUsername(String username);

}
