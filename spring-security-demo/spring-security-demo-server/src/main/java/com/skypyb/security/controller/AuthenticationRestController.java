package com.skypyb.security.controller;


import com.skypyb.security.config.SecurityConfiguration;
import com.skypyb.security.exception.RequestValidationException;
import com.skypyb.security.exception.SecurityAuthException;
import com.skypyb.security.model.request.AuthenticationRequest;
import com.skypyb.security.model.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
public class AuthenticationRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "${demo.security.route.auth_path}", method = RequestMethod.POST)
    public AuthenticationResponse createAuthenticationToken(@Valid @RequestBody AuthenticationRequest request, BindingResult validResult) {

        if (validResult.hasErrors()) {
            String errorMsg = validResult.getAllErrors().get(0).getDefaultMessage();
            throw new RequestValidationException(errorMsg);
        }

        //效验用户名和密码
        authenticate(request.getUserName(), request.getPassword());


        //如果效验正确，那就可以生成token了
        String token = "afasegjawbgkjab.asda.3666";

        return new AuthenticationResponse(token);
    }


    /**
     * 验证用户。如果出现问题，将抛出{@link SecurityAuthException}
     * 这个出错只会因为俩原因，要不是用户被禁用了，要不就是密码错误
     * 效验流程和机制可以看 {@link SecurityConfiguration#authenticationManagerBean()} 上的注解
     *
     * @param username 用户名
     * @param password 密码
     * @return Authentication
     */
    private Authentication authenticate(String username, String password) {

        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new SecurityAuthException("User is disabled!");
        } catch (BadCredentialsException e) {
            throw new SecurityAuthException("Wrong password!");
        }
    }

}
