package com.skypyb.security.controller.advice;

import com.skypyb.security.exception.SecurityAuthException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class AuthenticationExceptionAdvice {


    /**
     * 捕获到的异常 SecurityAuthException 不抛出报错,而是
     *
     * @param e SecurityAuthException
     * @return SecurityAuthException json
     */
    @ExceptionHandler({SecurityAuthException.class})
    public SecurityAuthException handleAuthenticationException(SecurityAuthException e) {
        return e;
    }
}
