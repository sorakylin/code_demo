package com.skypyb.security.controller;


import com.skypyb.security.exception.SecurityAuthException;
import com.skypyb.security.model.request.AuthenticationRequest;
import com.skypyb.security.model.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/tk")
public class AuthenticationRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "${demo.security.route.authentication.path}", method = RequestMethod.POST)
    public AuthenticationResponse createAuthenticationToken(@Valid AuthenticationRequest request, BindingResult validResult) {

        if (validResult.hasErrors()) {
            String errorMsg = validResult.getAllErrors().get(0).getDefaultMessage();
            throw new SecurityAuthException(errorMsg);
        }


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());
        //效验用户名和密码
        authenticationManager.authenticate(authenticationToken);


        //如果效验正确，那就可以生成token了
        String token = "";

        return new AuthenticationResponse(token);
    }


}
