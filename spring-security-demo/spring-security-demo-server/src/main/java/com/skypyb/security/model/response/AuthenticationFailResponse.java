package com.skypyb.security.model.response;


import com.skypyb.security.exception.SecurityAuthException;

/**
 * 认证失败时返回给前台的响应
 */
public class AuthenticationFailResponse extends ExceptionResponse {


    public AuthenticationFailResponse(int code, String message) {
        super(code, message);
    }

    public static AuthenticationFailResponse from(SecurityAuthException e) {
        return new AuthenticationFailResponse(e.getCode(), e.getMessage());
    }

}
