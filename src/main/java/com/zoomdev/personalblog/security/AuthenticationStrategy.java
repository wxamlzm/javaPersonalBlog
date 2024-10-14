package com.zoomdev.personalblog.security;

import com.zoomdev.personalblog.Response;
import com.zoomdev.personalblog.model.dto.JwtRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface AuthenticationStrategy {
    Response<?> authenticate(JwtRequest authRequest) throws Exception;
    void configure(HttpSecurity httpSecurity) throws Exception;
}
