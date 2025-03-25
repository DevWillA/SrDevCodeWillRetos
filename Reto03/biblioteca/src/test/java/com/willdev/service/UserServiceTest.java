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
        userService.addUser("2", "Alice","sss");
        User user = userService.findUser("2");
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
    void testDeleteUserSuccess() {
        userService.addUser("2", "Alice","sss");
        userService.deleteUser("2");
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findUser("2");
        });
        assertEquals("El usuario con el id 2 no existe", exception.getMessage());
    }

    @Test
    void testDeleteUserNotFound() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser("999");
        });
        assertEquals("El usuario con el id 999 no existe, no se puede eliminar", exception.getMessage());
    }

   
    @Test
    void testUpdateUserSuccess() {
        userService.addUser("2", "Alice","sss");
        userService.updateUserName("2", "Alice Maravilla");
        User user = userService.findUser("2");
        assertNotNull(user);
        assertEquals("Alice Maravilla", user.getName());
    }


    @Test
    void testUpdateUserNotFound() {
        userService.addUser("2", "Alice","sss");
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
        userService.updateUserName("3", "Alice Maravilla"); });
        assertEquals("El usuario con el id 3 no existe, no se puede actualizar nombre", exception.getMessage());
    }

    @Test
    void testUpdateUserEmailSuccess() {
        userService.addUser("2", "Alice","sss");
        userService.updateUserEmail("2", "gmail");
        User user = userService.findUser("1");
        assertNotNull(user);
        assertEquals("william@example.com", user.getEmail());

    }

    @Test
    void testUpdateUserEmailNotFound() {
        userService.addUser("2", "Alice","sss");
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
        userService.updateUserEmail("3", "gmail"); });
        assertEquals("El usuario con el id 3 no existe, no se puede actualizar correo", exception.getMessage());
    }


    @Test
    void testAddUserFind() {
        userService.addUser("2", "Amaya", "amaya@example.com");
        User user = userService.findUser("2");
        assertNotNull(user);
        assertEquals("Amaya", user.getName());
    }

    @Test
    void testFindUser_Success() {
        User user = userService.findUser("1");
        assertNotNull(user);
        assertEquals("William", user.getName());
    }

    @Test
    void testFindUser_NotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.findUser("99"));
    }

    @Test
    void testDeleteUser_Success() {
        userService.deleteUser("1");
        assertThrows(UserNotFoundException.class, () -> userService.findUser("1"));
    }

    @Test
    void testDeleteUser_NotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser("99"));
    }

    @Test
    void testUpdateUserName_Success() {
        userService.updateUserName("1", "Will");
        assertEquals("Will", userService.findUser("1").getName());
    }

    @Test
    void testUpdateUserName_NotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.updateUserName("99", "John"));
    }

    @Test
    void testUpdateUserEmail_Success() {
        userService.updateUserEmail("1", "will@new.com");
        assertEquals("will@new.com", userService.findUser("1").getEmail());
    }

    @Test
    void testUpdateUserEmail_NotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.updateUserEmail("99", "john@new.com"));
    }
    


}
