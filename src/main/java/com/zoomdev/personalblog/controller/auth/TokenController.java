package com.zoomdev.personalblog.controller.auth;


import com.zoomdev.personalblog.Response;
import com.zoomdev.personalblog.model.dto.JwtRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class TokenController extends BaseAuthController{

    @PostMapping("verify")
    public ResponseEntity<Response<?>> verifyToken(@RequestBody JwtRequest tokenRequest) throws Exception{
        return authenticateInternal(tokenRequest);
    }
}
