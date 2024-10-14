package com.zoomdev.personalblog.model;

public class JwtAuthenticationData {
    private final String token;

    public JwtAuthenticationData(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
