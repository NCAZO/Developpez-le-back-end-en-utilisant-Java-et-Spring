package com.openclassrooms.chatop.controllers;

import com.openclassrooms.chatop.models.User;
import com.openclassrooms.chatop.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private AuthService authService;

    @GetMapping("/api/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return authService.getUserById(id);
    }

}
