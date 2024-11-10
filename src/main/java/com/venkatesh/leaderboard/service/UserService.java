package com.venkatesh.leaderboard.service;

import com.venkatesh.leaderboard.exception.UserNotFoundException;
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
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    user.setScore(newScore);
    // Assign badges based on newScore (optional, based on requirements)
    return userRepository.save(user);
    }

    public void deleteUserById(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }
}
