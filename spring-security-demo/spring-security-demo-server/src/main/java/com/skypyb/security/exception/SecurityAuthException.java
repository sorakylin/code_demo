package com.skypyb.security.exception;


public class SecurityAuthException extends RuntimeException {

    private int code;

    private String message;

    public SecurityAuthException(String message) {
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
