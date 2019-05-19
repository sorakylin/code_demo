package com.skypyb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private Long userId;
    private String userName;
    private String password;
    private String idCard;
    private String phone;
    private String sex;
    private Authority authority;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if (Objects.equals(sex, "男") || Objects.equals(sex, "女")) {
            this.sex = sex;
        } else throw new IllegalArgumentException("User.setSex() method argument incorrect.");
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("userId=").append(userId);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", idCard='").append(idCard).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", authority=").append(authority);
        sb.append('}');
        return sb.toString();
    }
}
