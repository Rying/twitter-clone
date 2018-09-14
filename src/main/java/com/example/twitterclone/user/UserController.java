package com.example.twitterclone.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserInfo login(HttpServletRequest request) {
        String name = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userService.getUserInfo(name);
    }

    @PostMapping("/api/user/create")
    public void createUser(@RequestBody UserInfo userInfo) {
        userService.create(userInfo);
    }

}
