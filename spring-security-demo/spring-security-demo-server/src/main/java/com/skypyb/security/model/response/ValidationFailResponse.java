package com.skypyb.security.model.response;


import com.skypyb.security.exception.RequestValidationException;


/**
 * 数据效验失败时返回的响应
 */
public class ValidationFailResponse extends ExceptionResponse {

    public ValidationFailResponse(int code, String message) {
        super(code, message);
    }

    public static ValidationFailResponse from(RequestValidationException e) {
        return new ValidationFailResponse(e.getCode(), e.getMessage());
    }

}
