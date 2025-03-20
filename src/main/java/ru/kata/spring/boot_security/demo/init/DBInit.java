package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Component
public class DBInit {

    @Autowired
    public DBInit(UserServiceImpl userService) {
        userService.initializeDB();
    }
}
