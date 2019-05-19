package com.skypyb.controller;

import com.skypyb.entity.User;
import com.skypyb.model.LaydataResponse;
import com.skypyb.service.UserService;
import com.mysql.cj.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        logger.info("Access UserController.getUser() id = {}", id);

        return userService.getUser(id);
    }

    @GetMapping("/name/{userName}")
    public User getUser(@PathVariable("userName") String userName) {
        logger.info("Access UserController.getUser() userName = {}", userName);

        return userService.getUser(userName);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {

        logger.info("Access AuthorityController.deleteUser() id={}", id);
        userService.delete(id);
    }

    @PutMapping("/add")
    public String addUser(@RequestBody User user) {
        logger.info("Access AuthorityController.addUser() :  {}", user);

        if (userService.getUser(user.getUserName()) != null) return "existed";

        userService.addUser(user);
        return "success";
    }

    @PutMapping("/edit")
    public String editUser(@RequestBody User user) {
        logger.info("Access AuthorityController.editUser() : {}", user);

        userService.editUser(user);
        return "success";
    }

    @GetMapping("/tablePage")
    public LaydataResponse<?> tableGetUser(int page, int limit) {
        logger.info("Access UserController.getUsers()");

        List<User> users = userService.findAllByPage(page == 1 ? 0 : (page * limit) - limit, limit);

        LaydataResponse<User> response = new LaydataResponse<>();
        response.setData(users);
        response.setCount(userService.count());


        return response;
    }

    @PostMapping("/login")
    public String login(String userName, String password, HttpSession session) {
        logger.info("Access UserController.login()  userName:{},password:{}", userName, password);

        User user = userService.getUser(userName);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loginUser", user);
            return "success";
        }

        return "field";
    }

}
