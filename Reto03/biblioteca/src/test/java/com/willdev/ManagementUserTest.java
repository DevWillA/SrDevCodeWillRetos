package com.willdev;

import org.junit.jupiter.api.Test;

public class ManagementUserTest {

        @Test
        void testAddUser() {
                ManagementUsers managementUser = new ManagementUsers();
                managementUser.addUser("1", "Will");
                managementUser.addUser("2", "Will");
                managementUser.addUser("3", "Will");
        }

        @Test
        void testFindUser() {
                ManagementUsers managementUser = new ManagementUsers();
                managementUser.addUser("1", "Will");
                managementUser.addUser("2", "Will");
                managementUser.addUser("3", "Will");
                managementUser.findUser("1");
        }

}
