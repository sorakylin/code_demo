package com.skypyb.security.controller.advice;

import com.skypyb.security.exception.RequestValidationException;
import com.skypyb.security.exception.SecurityAuthException;
import com.skypyb.security.model.response.AuthenticationFailResponse;
import com.skypyb.security.model.response.ValidationFailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class AuthenticationExceptionAdvice {


    /**
     * 捕获到的异常 SecurityAuthException 不抛出报错,而是返回 AuthenticationFailResponse
     *
     * @param e SecurityAuthException
     * @return SecurityAuthException json
     */
    @ExceptionHandler({SecurityAuthException.class})
    public ResponseEntity<?> handleSecurityAuthException(SecurityAuthException e) {
        return ResponseEntity.ok().body(AuthenticationFailResponse.asResponse(e));
    }

    @ExceptionHandler({RequestValidationException.class})
    public ResponseEntity<?> handleRequestValidationException(RequestValidationException e) {
        return ResponseEntity.ok().body(ValidationFailResponse.from(e));
    }
}
