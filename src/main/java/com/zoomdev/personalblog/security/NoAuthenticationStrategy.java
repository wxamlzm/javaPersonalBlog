package com.zoomdev.personalblog.security;

import com.zoomdev.personalblog.Response;
import com.zoomdev.personalblog.model.dto.JwtRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Component;

@Component("noAuthenticationStrategy")
public class NoAuthenticationStrategy implements AuthenticationStrategy {
    @Override
    public Response<?> authenticate(JwtRequest authRequest) throws Exception {
        // 在无认证模式下，我们可以简单的返回一个成功响应
        return Response.newSuccess(null);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
