package com.zoomdev.personalblog.controller.auth;

import com.zoomdev.personalblog.Response;
import com.zoomdev.personalblog.model.dto.JwtRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class LoginController extends BaseAuthController {

    @PostMapping("/login")
    public ResponseEntity<Response<?>> login(@Valid @RequestBody JwtRequest loginRequest) throws Exception {
        return authenticateInternal(loginRequest);
    }
}
