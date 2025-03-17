package com.willdev.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.willdev.exception.UserNotFoundException;
import com.willdev.model.User;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testAddUser() {
        userService.addUser("1", "Alice","sss");
        User user = userService.findUser("1");
        assertNotNull(user);
        assertEquals("Alice", user.getName());
    }

    @Test
    void testAddAndFindUser_Success() {
        userService.addUser("1", "Alice","sss");
        User user = userService.findUser("1");
        assertNotNull(user);
        assertEquals("Alice", user.getName());
    }

    @Test
    void testFindUser_NotFound() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findUser("999");
        });
        assertEquals("El ususario con el id 999 no existe", exception.getMessage());
    }

    @Test
    void testFindUserById_NotFound() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findUser("999");
        });
        assertEquals("El ususario con el id 999 no existe", exception.getMessage());
    }
}
