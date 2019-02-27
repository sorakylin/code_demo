package com.skypyb;

import com.skypyb.entity.User;
import com.skypyb.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventTest {

    @Autowired
    private UserService userService;

    @Test
    public void test1() {
        User u = new User() {{
            setId(1L);
            setUserName("skypyb");
            setPassword(UUID.randomUUID().toString());
        }};

        userService.register(u);
    }
}
