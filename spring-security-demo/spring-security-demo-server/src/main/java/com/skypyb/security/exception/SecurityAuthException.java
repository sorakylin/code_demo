package com.skypyb.security.exception;


import org.springframework.security.core.AuthenticationException;

public class SecurityAuthException extends AuthenticationException {

    private int code;

    private String message;

    public SecurityAuthException(String message) {
        super(message);
        this.code = 401;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
