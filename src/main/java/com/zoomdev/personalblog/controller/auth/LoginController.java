package com.zoomdev.personalblog.controller.auth;

import com.zoomdev.personalblog.Response;
import com.zoomdev.personalblog.model.dto.JwtRequest;
import com.zoomdev.personalblog.security.AuthenticationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class LoginController extends BaseAuthController {
    @Autowired
    private AuthenticationStrategy authenticationStrategy;

    @PostMapping("/authenticate")
    public ResponseEntity<Response<?>> authenticate(@RequestBody JwtRequest authenRequest) throws Exception {
        Response<?> response = authenticationStrategy.authenticate(authenRequest);
        return ResponseEntity.ok(response);
    }
}
