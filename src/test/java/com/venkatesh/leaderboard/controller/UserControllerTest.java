package com.venkatesh.leaderboard.controller;

import com.venkatesh.leaderboard.model.User;
import com.venkatesh.leaderboard.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // Test GET /users - Retrieve all users
    @Test
    public void testGetAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(
                new User("user123", "john_doe", 0, Set.of()),
                new User("user456", "jane_smith", 25, Set.of("Code Ninja"))
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userId", is("user123")))
                .andExpect(jsonPath("$[0].username", is("john_doe")))
                .andExpect(jsonPath("$[1].userId", is("user456")))
                .andExpect(jsonPath("$[1].username", is("jane_smith")));
    }

    // Test GET /users/{userId} - Retrieve a user by ID
    @Test
    public void testGetUserById() throws Exception {
        Mockito.when(userService.getUserById("user123"))
                .thenReturn(Optional.of(new User("user123", "john_doe", 10, Set.of("Code Ninja"))));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/user123")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is("user123")))
                .andExpect(jsonPath("$.username", is("john_doe")))
                .andExpect(jsonPath("$.score", is(10)))
                .andExpect(jsonPath("$.badges", hasItem("Code Ninja")));
    }

    // Test POST /users - Register a new user
    @Test
    public void testRegisterUser() throws Exception {
        User newUser = new User("user789", "alice_smith", 0, Set.of());
        Mockito.when(userService.registerUser(Mockito.any(User.class))).thenReturn(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"user789\", \"username\":\"alice_smith\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is("user789")))
                .andExpect(jsonPath("$.username", is("alice_smith")))
                .andExpect(jsonPath("$.score", is(0)))
                .andExpect(jsonPath("$.badges", empty()));
    }

    // Test PUT /users/{userId} - Update a user’s score
    @Test
    public void testUpdateUserScore() throws Exception {
        User updatedUser = new User("user123", "john_doe", 45, Set.of("Code Ninja"));
        Mockito.when(userService.updateUserScore("user123", 45)).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/user123")
        .contentType(MediaType.APPLICATION_JSON)
        .content("45"))  // Adjust JSON structure as per your method's requirements
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId", is("user123")))
        .andExpect(jsonPath("$.score", is(45)))
        .andExpect(jsonPath("$.badges", hasItem("Code Ninja")));  // Verify badges if assigned based on score

    }

    // Test DELETE /users/{userId} - Delete a user by ID
    @Test
    public void testDeleteUserById() throws Exception {
        Mockito.doNothing().when(userService).deleteUserById("user123");

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/user123")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
    @Test
    public void testGetUserById_UserExists() throws Exception {
        // Arrange
        String userId = "user123";
        User user = new User(userId, "john_doe", 50, Set.of("Code Master"));
        Mockito.when(userService.getUserById(userId)).thenReturn(Optional.of(user));
    
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.score").value(50))
                .andExpect(jsonPath("$.badges", hasItem("Code Master")));
    }
    
    @Test
    public void testGetUserById_UserNotFound() throws Exception {
        // Arrange
        String nonExistentUserId = "nonExistentUser";
        Mockito.when(userService.getUserById(nonExistentUserId)).thenReturn(Optional.empty());
    
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", nonExistentUserId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID " + nonExistentUserId + " not found"));
    }
    
    
    
}
