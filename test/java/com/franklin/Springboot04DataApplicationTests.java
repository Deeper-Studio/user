package com.franklin;

import com.franklin.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot04DataApplicationTests {
    @Autowired
    UserServiceImpl userService;

    @Test
    void contextLoads(){
        System.out.println(userService.queryUserByName("Nan Jing"));
    }
}
