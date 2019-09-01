package com.skypyb.security.model.dto;

import com.skypyb.user.model.dto.MinimumUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Spring Security框架服务的用户类
 *
 * @author pyb
 * @date 2019/28/31
 */
public class AuthenticationUser implements UserDetails {

    private Long userId;
    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public static final AuthenticationUser from(MinimumUserDTO user) {
        AuthenticationUser authUser = new AuthenticationUser();
        authUser.setUserId(user.getUserId());
        authUser.setUserName(user.getUserName());
        authUser.setPassword(user.getPassword());
        return authUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * 以下用户认证默认返回true，具体权限自己设计，不交给SpringSecurity
     * -------------------------------------------------------
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
