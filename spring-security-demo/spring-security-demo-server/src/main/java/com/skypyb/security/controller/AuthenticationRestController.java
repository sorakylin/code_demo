package com.skypyb.security.controller;


import com.skypyb.security.model.request.AuthenticationRequest;
import com.skypyb.security.model.response.AuthenticationResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/tk")
public class AuthenticationRestController {


    @RequestMapping(value = "${security.route.authentication.path}", method = RequestMethod.POST)
    public AuthenticationResponse createAuthenticationToken(@Valid AuthenticationRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());

        String token = "";

        return new AuthenticationResponse(token);
    }


}
