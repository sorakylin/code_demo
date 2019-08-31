package com.skypyb.security.service;

import com.skypyb.security.exception.SecurityAuthException;
import com.skypyb.user.service.UserService;
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
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!username.equals("skypyb")) {
            throw new SecurityAuthException("User name not found!");
        }

        //真实系统需要从数据库或缓存中获取，我这随便返回个用户，前端传过来的用户名和密码必须和下边这个一样才通过认证
        return User.builder().username("skypyb").password(encoder.encode("666666")).roles("USER").build();

    }
}
