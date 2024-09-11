package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.models.User;
import com.ovo.app.ovo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping(path = "/registration")
    public void createBankAccount(@RequestBody User user) {
        userService.addNewUser(user);
    }
}
