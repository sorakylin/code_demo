package com.skypyb.sc.service;

import com.skypyb.sc.dao.UserDao;
import com.skypyb.sc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;


    public User getUser(Long id){
        return  userDao.findById(id);
    }

}
