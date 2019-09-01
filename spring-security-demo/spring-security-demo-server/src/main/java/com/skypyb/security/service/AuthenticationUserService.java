package com.skypyb.security.service;

import com.skypyb.security.exception.SecurityAuthException;
import com.skypyb.security.model.dto.AuthenticationUser;
import com.skypyb.user.model.po.UserPO;
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

    /**
     * 这里返回的用户最终会和{@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken}
     * 这个里面的用户名密码进行比对，如果成功了就走 success handler 失败反之
     *
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!username.equals("skypyb")) {
            throw new SecurityAuthException("User name not found!");
        }

        UserPO user = userService.findUser(username);
        if (user == null) {
            throw new SecurityAuthException("User not found!");
        }

        return AuthenticationUser.from();
    }
}
