package com.skypyb.security.model.response;

/**
 * 认证成功响应
 */
public class AuthenticationResponse extends BasicResponse {

    public AuthenticationResponse(String message) {
        super(200, message);
    }
}
