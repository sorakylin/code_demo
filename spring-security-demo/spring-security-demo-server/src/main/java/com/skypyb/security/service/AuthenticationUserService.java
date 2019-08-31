package com.skypyb.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null) {
            throw new UsernameNotFoundException("Username not found!");
        }

        //真实系统需要从数据库或缓存中获取，我这随便返回个用户，前端传过来的用户名和密码必须和下边这个一样才通过认证
        return User.builder().username("skypyb").password(encoder.encode("666666")).roles("USER").build();

    }
}
