package com.skypyb.user.service;

import com.skypyb.user.model.po.UserPO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //private UserDao dao;



    public UserPO findUser(Long userId){
        return new UserPO();
    }


    public UserPO findUser(String userName){
        return new UserPO();
    }


}
