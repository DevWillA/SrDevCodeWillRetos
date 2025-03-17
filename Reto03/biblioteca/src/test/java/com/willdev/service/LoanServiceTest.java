package com.willdev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.willdev.model.Loan;

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
        userService.addUser("1", "Alice", "Alice@gmail.com");
    }

    @Test
    void testAddLoan() {
        assertDoesNotThrow(() -> loanService.addLoan("1", "1"));
        Loan loan = loanService.getLoan("1");
        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());
    }

    @Test
    void testAddLoan_Success() {
        assertDoesNotThrow(() -> loanService.addLoan("1", "1"));
        Loan loan = loanService.getLoan("1");
        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());

    }


    @Test
    void testGetLoansByUser() {
        loanService.addLoan("1", "1");
        Loan loan = loanService.getLoan("1");

        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());
    }

    @Test
    void testGetLoansByUser_NoLoans() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> { // Usa la excepción correcta
            loanService.getLoan("999");
        });
    
        assertEquals("El usuario con el id 999 no tiene prestamos", exception.getMessage()); // Verifica mensaje
    }

}
