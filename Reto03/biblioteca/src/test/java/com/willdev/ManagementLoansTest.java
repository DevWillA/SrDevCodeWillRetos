package com.willdev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManagementLoansTest {
    private ManagementBooks managementBooks;
    private ManagementUsers managementUsers;
    private ManagementLoans managementLoans;

    @BeforeEach
    void setUp() {
        managementBooks = new ManagementBooks();
        managementUsers = new ManagementUsers();
        managementLoans = new ManagementLoans(managementBooks, managementUsers);

        managementBooks.addBook("1", "Java Programming", "John Doe");
        managementUsers.addUser("1", "Alice");
    }

    @Test
    void testAddLoan_Success() {
        assertDoesNotThrow(() -> managementLoans.addLoans("1", "1"));
        Loans loan = managementLoans.getLoans("1");
        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());

    }

    @Test
    void testAddLoan_BookAlreadyLoaned() {
        assertDoesNotThrow(() -> managementLoans.addLoans("1", "1"));
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            managementLoans.addLoans("2", "1"); // Otro usuario intenta el mismo libro
        });
        assertTrue(exception.getMessage().contains("El libro 'Java Programming' ya está prestado"));
    }
}
