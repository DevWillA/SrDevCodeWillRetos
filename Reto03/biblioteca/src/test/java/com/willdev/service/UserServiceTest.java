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
    void testAddAndFindUserSuccess() {
        userService.addUser("1", "Alice","sss");
        User user = userService.findUser("1");
        assertNotNull(user);
        assertEquals("Alice", user.getName());
    }

    @Test
    void testFindUserNotFound() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findUser("999");
        });
        assertEquals("El usuario con el id 999 no existe", exception.getMessage());
    }

    @Test
    void testFindUserByIdNotFound() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findUser("999");
        });
        assertEquals("El usuario con el id 999 no existe", exception.getMessage());
    }
}
