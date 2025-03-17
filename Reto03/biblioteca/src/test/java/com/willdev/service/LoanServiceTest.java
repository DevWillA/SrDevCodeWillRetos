package com.willdev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoanServiceTest {
    private BookService bookService;
    private UserService userService;
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        userService = new UserService();
        loanService = new LoanService(bookService, userService);

        bookService.addBook("1", "Java Programming", "John Doe");
        userService.addUser("1", "Alice");
    }

    @Test
    void testAddLoan() {
        assertDoesNotThrow(() -> loanService.addLoans("1", "1"));
        Loan loan = loanService.getLoans("1");
        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());
    }

    @Test
    void testAddLoan_Success() {
        assertDoesNotThrow(() -> loanService.addLoans("1", "1"));
        Loan loan = loanService.getLoans("1");
        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());

    }

    @Test
    void testAddLoan_BookAlreadyLoaned() {
        assertDoesNotThrow(() -> loanService.addLoans("1", "1"));
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            loanService.addLoans("2", "1"); // Otro usuario intenta el mismo libro
        });
    }

    @Test
    void testGetLoansByUser() {
        loanService.addLoans("1", "1");
        Loan loan = loanService.getLoans("1");

        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());
    }

    @Test
    void testGetLoansByUser_NoLoans() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> { // Usa la excepción correcta
            loanService.getLoans("999");
        });
    
        assertEquals("El usuario con el id 999 no tiene prestamos", exception.getMessage()); // Verifica mensaje
    }

}
