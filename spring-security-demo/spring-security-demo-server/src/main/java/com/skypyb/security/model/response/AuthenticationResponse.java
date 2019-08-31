package com.skypyb.security.model.response;

/**
 * 认证响应，包含一个token
 */
public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
