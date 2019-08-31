package com.skypyb.security.controller.advice;

import com.skypyb.security.exception.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class AuthenticationExceptionAdvice {


    /**
     * 捕获到的异常 AuthenticationException 不抛出报错,而是
     *
     * @param e AuthenticationException
     * @return AuthenticationException json
     */
    @ExceptionHandler({AuthenticationException.class})
    public AuthenticationException handleAuthenticationException(AuthenticationException e) {
        return e;
    }
}
