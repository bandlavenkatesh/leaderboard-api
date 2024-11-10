package com.venkatesh.leaderboard.controller;

import com.venkatesh.leaderboard.model.User;
import com.venkatesh.leaderboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserScore(@PathVariable String userId, @RequestBody int newScore) {
        try {
            return ResponseEntity.ok(userService.updateUserScore(userId, newScore));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
