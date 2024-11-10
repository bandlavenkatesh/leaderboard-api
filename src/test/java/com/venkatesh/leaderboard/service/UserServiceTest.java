package com.venkatesh.leaderboard.service;

import com.venkatesh.leaderboard.exception.UserNotFoundException;
import com.venkatesh.leaderboard.model.User;
import com.venkatesh.leaderboard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        User user = new User("user123", "john_doe", 0, new HashSet<>());
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.registerUser(user);

        assertNotNull(savedUser);
        assertEquals("user123", savedUser.getUserId());
        assertEquals(0, savedUser.getScore());
        assertTrue(savedUser.getBadges().isEmpty());
    }

    @Test
    void testGetUserById_UserExists() {
        User user = new User("user123", "john_doe", 0, new HashSet<>());
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById("user123");

        assertTrue(result.isPresent());
        assertEquals("user123", result.get().getUserId());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById("nonExistentUser")).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById("nonExistentUser");

        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateUserScore() {
        User user = new User("user123", "john_doe", 10, new HashSet<>());
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.updateUserScore("user123", 35);

        assertEquals(35, updatedUser.getScore());
        assertTrue(updatedUser.getBadges().contains("Code Champ"));
    }

    @Test
    void testDeleteUserById_UserNotFound() {
        when(userRepository.existsById("nonExistentUser")).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById("nonExistentUser"));
    }
}
