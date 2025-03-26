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
        userService.addUser("1", "William", "william@example.com");
    }

    @Test
    void testAddAndFindUserSuccess() {
        userService.addUser("2", "Alice", "alice@example.com");
        User user = userService.findUser("2");
        assertAll(
            () -> assertNotNull(user),
            () -> assertEquals("Alice", user.getName())
        );
    }

    @Test
    void testFindUserNotFound() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.findUser("999"));
        assertEquals("El usuario con el id 999 no existe", exception.getMessage());
    }

    @Test
    void testDeleteUserSuccess() {
        userService.addUser("2", "Alice", "alice@example.com");
        userService.deleteUser("2");
        assertThrows(UserNotFoundException.class, () -> userService.findUser("2"));
    }

    @Test
    void testDeleteUserNotFound() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.deleteUser("999"));
        assertEquals("El usuario con el id 999 no existe, no se puede eliminar", exception.getMessage());
    }

    @Test
    void testUpdateUserNameSuccess() {
        userService.addUser("2", "Alice", "alice@example.com");
        userService.updateUserName("2", "Alice Maravilla");
        User user = userService.findUser("2");
        assertAll(
            () -> assertNotNull(user),
            () -> assertEquals("Alice Maravilla", user.getName())
        );
    }

    @Test
    void testUpdateUserNameNotFound() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.updateUserName("3", "Alice Maravilla"));
        assertEquals("El usuario con el id 3 no existe, no se puede actualizar nombre", exception.getMessage());
    }

    @Test
    void testUpdateUserEmailSuccess() {
        userService.updateUserEmail("1", "will@new.com");
        assertEquals("will@new.com", userService.findUser("1").getEmail());
    }

    @Test
    void testUpdateUserEmailNotFound() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.updateUserEmail("3", "new@example.com"));
        assertEquals("El usuario con el id 3 no existe, no se puede actualizar correo", exception.getMessage());
    }
    


}
