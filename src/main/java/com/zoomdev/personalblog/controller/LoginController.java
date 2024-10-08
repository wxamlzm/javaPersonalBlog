package com.zoomdev.personalblog.controller;

import com.zoomdev.personalblog.controller.auth.BaseAuthController;
import com.zoomdev.personalblog.model.dto.JwtRequest;
import com.zoomdev.personalblog.model.dto.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class LoginController extends BaseAuthController {
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception{
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
