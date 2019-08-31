package com.skypyb.security.model.response;


import com.skypyb.security.exception.SecurityAuthException;

/**
 * 认证失败时返回给前台的响应
 */
public class AuthenticationFailResponse {

    private int code;
    private String message;

    public AuthenticationFailResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static AuthenticationFailResponse from(SecurityAuthException e) {
        return new AuthenticationFailResponse(e.getCode(), e.getMessage());
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
