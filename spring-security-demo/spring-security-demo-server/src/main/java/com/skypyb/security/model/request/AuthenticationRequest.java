package com.skypyb.security.model.request;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * 登陆接口接收的请求
 *
 * @author pyb
 */
public class AuthenticationRequest {

    @NotBlank(message = "Username can not be blank!")
    private String userName;

    @NotBlank(message = "Password can not be blank!")
    @Range(min = 6, max = 36)
    private String password;

    //Verification code ...

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthenticationRequest{");
        sb.append("userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
