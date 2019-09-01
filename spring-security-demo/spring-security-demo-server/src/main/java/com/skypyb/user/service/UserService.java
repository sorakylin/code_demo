package com.skypyb.user.service;

import com.skypyb.user.dao.UserDao;
import com.skypyb.user.model.dto.MinimumUserDTO;
import com.skypyb.user.model.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao dao;


    public Optional<UserPO> findUserByUserName(String userName) {
        return dao.findUserByUserName(userName);
    }

    public Optional<UserPO> findUserByUserId(String userId) {

        return dao.findUserByUserId(userId);
    }

    public Optional<MinimumUserDTO> findMinimumUser(String userName) {
        return dao.findMinimumUser(userName);
    }


}
