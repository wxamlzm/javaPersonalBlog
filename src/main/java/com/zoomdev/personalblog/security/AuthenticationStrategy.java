package com.zoomdev.personalblog.security;

import com.zoomdev.personalblog.Response;
import com.zoomdev.personalblog.model.dto.JwtRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface AuthenticationStrategy {
    // 用与处理认证逻辑
    // 无论是jwt认证还是无认证，都需要实现这个方法
    Response<?> authenticate(JwtRequest authRequest) throws Exception;

    // configure 方法用于配置 Spring Security
    // 这个方法允许每种策略定制自己的安全配置
    void configure(HttpSecurity httpSecurity) throws Exception;
}
