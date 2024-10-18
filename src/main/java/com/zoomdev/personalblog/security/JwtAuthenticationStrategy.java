package com.zoomdev.personalblog.security;

import com.zoomdev.personalblog.Response;
import com.zoomdev.personalblog.model.JwtAuthenticationData;
import com.zoomdev.personalblog.model.dto.JwtRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component("jwtAuthenticationStrategy")
public class JwtAuthenticationStrategy implements AuthenticationStrategy {
    private  AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    public Response<?> authenticate(JwtRequest authRequest) throws Exception {
        // 添加参数验证
        if(authRequest == null){
            return Response.newFail("Authentication request cannot be null");
        }

        if(StringUtils.isNotBlank(authRequest.getToken())){
            return validateToken(authRequest.getToken());
        }else if (StringUtils.isNotBlank(authRequest.getUsername()) && StringUtils.isNotBlank(authRequest.getPassword())) {
            return authenticateWithCredentials(authRequest.getUsername(), authRequest.getPassword());
        } else {
            return Response.newFail("Invalid authentication request. Provide either a token or username and password");
        }
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private Response<?> authenticateWithCredentials(String username, String password) throws Exception {
        // 添加额外的参数验证
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return Response.newFail("Username and password must nott be blank");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            final String token = jwtTokenUtil.generateToken(userDetails);
            return Response.newSuccess(new JwtAuthenticationData(token));
        }catch(AuthenticationException e){
            // 记录错误日志
            return Response.newFail("Authentication failed");
        }
    }

    private Response<?> validateToken(String token) {
        if(StringUtils.isBlank(token)){
            return Response.newFail("Token must not be blank");
        }

        try {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(token, userDetails)) {
                String newToken = jwtTokenUtil.refreshToken(token);
                return Response.newSuccess(new JwtAuthenticationData(newToken));
            } else {
                return Response.newFail("Invalid token");
            }
        }catch( Exception e ){
            // 记录错误日志
            return Response.newFail("Token validation failed");
        }

    }

}
