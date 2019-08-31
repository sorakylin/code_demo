package com.skypyb.security.model.dto;

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
