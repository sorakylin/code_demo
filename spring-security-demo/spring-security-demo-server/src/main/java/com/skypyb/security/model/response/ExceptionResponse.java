package com.skypyb.security.model.response;


public class ExceptionResponse {

    private int code;
    private String message;


    public ExceptionResponse() {
    }

    public ExceptionResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
