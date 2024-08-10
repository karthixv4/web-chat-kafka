package com.example.demo.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Group;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody String username) {
        User user = userService.createUser(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{userId}/groups")
    public ResponseEntity<Set<Group>> getUserGroups(@PathVariable Long userId) {
        Set<Group> groups = userService.getUserGroups(userId);
        return ResponseEntity.ok(groups);
    }
}
