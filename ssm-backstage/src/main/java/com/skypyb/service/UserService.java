package com.skypyb.service;

import com.skypyb.dao.UserDao;
import com.skypyb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;


    public User getUser(Long id) {
        return userDao.getUserById(id);
    }

    public User getUser(String userName) {
        return userDao.getUserByName(userName);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public List<User> findAllByPage(int page, int limit) {
        return userDao.findAllByPage(page, limit);
    }

    public int count() {
        return userDao.count();
    }

    public void delete(Long userId) {
        userDao.delete(userId);
    }

    public int addUser(User user) {
        return userDao.addUser(user);
    }

    public int editUser(User user) {
        User temp = null;

        if (user.getUserId() != null) {
            temp = userDao.getUserById(user.getUserId());
        } else if (user.getUserName() != null) {
            temp = userDao.getUserByName(user.getUserName());
        } else {
            throw new IllegalArgumentException("No such user.");
        }

        user.setUserId(temp.getUserId());
        user.setUserName(temp.getUserName());
        user.setPassword(temp.getPassword());

        return userDao.editUser(user);
    }


}
