package com.zoomdev.personalblog.controller.auth;

import com.zoomdev.personalblog.security.JwtTokenUtil;
import com.zoomdev.personalblog.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class BaseAuthController {
    private AuthenticationManager authenticationManager;

    @Autowired
    protected JwtTokenUtil jwtTokenUtil;

    @Autowired
    protected JwtUserDetailsService userDetailsService;

    protected void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    protected String generateToken(UserDetails userDetails) {
        return jwtTokenUtil.generateToken(userDetails);
    }
}
