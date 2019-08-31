package com.skypyb.security.exception;

public class RequestValidationException extends RuntimeException {

    private int code;

    private String message;

    public RequestValidationException(String message) {
        this.code = 400;
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
