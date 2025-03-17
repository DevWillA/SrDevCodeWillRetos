package com.willdev;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManagementUsersTest {
    private ManagementUsers managementUsers;

    @BeforeEach
    void setUp() {
        managementUsers = new ManagementUsers();
    }

    @Test
    void testAddUser() {
        managementUsers.addUser("1", "Alice");
        Users user = managementUsers.findUser("1");
        assertNotNull(user);
        assertEquals("Alice", user.getName());
    }

    @Test
    void testAddAndFindUser_Success() {
        managementUsers.addUser("1", "Alice");
        Users user = managementUsers.findUser("1");
        assertNotNull(user);
        assertEquals("Alice", user.getName());
    }

    @Test
    void testFindUser_NotFound() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            managementUsers.findUser("999");
        });
        assertEquals("El ususario con el id 999 no existe", exception.getMessage());
    }

    @Test
    void testFindUserById_NotFound() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            managementUsers.findUser("999");
        });
        assertEquals("El ususario con el id 999 no existe", exception.getMessage());
    }
}
