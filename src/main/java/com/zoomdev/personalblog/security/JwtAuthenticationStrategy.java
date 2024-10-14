package com.zoomdev.personalblog.security;

import com.zoomdev.personalblog.Response;
import com.zoomdev.personalblog.model.JwtAuthenticationData;
import com.zoomdev.personalblog.model.dto.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component("jwtAuthenticationStrategy")
public class JwtAuthenticationStrategy implements AuthenticationStrategy{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    public Response<?> authenticate(JwtRequest authRequest) throws Exception{
        if(authRequest.getToken() != null && !authRequest.getToken().isEmpty()){
            return validateToken(authRequest.getToken());
        }else {
            return authenticateWithCredentials(authRequest.getUsername(), authRequest.getPassword());
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

    private Response<?> authenticateWithCredentials(String username, String password) throws Exception{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return Response.newSuccess(new JwtAuthenticationData(token));
    }



    private Response<?> validateToken(String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtTokenUtil.validateToken(token,userDetails)){
            String newToken = jwtTokenUtil.refreshToken(token);
            return Response.newSuccess(new JwtAuthenticationData(newToken));
        }else {
            return Response.newFail("Invalid token");
        }
    }

}
