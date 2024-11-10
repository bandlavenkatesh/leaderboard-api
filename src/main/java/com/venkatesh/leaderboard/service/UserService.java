package com.venkatesh.leaderboard.service;

import com.venkatesh.leaderboard.model.User;
import com.venkatesh.leaderboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparingInt(User::getScore).reversed());
        return users;
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public User registerUser(User user) {
        user.setScore(0);
        user.getBadges().clear();
        return userRepository.save(user);
    }

    public User updateUserScore(String userId, int newScore) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setScore(newScore);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }
}
