package com.akshat.journalApp.controller;

import com.akshat.journalApp.model.User;
import com.akshat.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Journal App is up and running!";
    }

}
