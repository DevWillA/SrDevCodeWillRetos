package com.willdev.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.willdev.exception.NoSuchElementException;
import com.willdev.exception.UserNotFoundException;
import com.willdev.model.Book;
import com.willdev.model.Loan;
import com.willdev.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoanService loanService;

    // Datos de prueba
    private User testUser;
    private Book testBook;

    @BeforeEach
    public void setUp() {
        testUser = new User("1", "Alice", "alice@example.com");
        testBook = new Book("1", "Effective Java", "Joshua Bloch");
    }

    @Test
    public void testAddLoanSuccesss() {
        when(userService.findUser("1")).thenReturn(testUser);
        when(bookService.findBook("1")).thenReturn(testBook);

        loanService.addLoan("1", "1");

        Loan loan = loanService.getLoanByUser("1");

        assertNotNull(loan);
        assertEquals(testUser, loan.getUser());
        assertEquals(testBook, loan.getBook());
    }

    @Test
    public void testAddLoanUserNotFound() {
        when(userService.findUser("999"))
                .thenThrow(new UserNotFoundException("User with ID 999 not found"));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            loanService.addLoan("999", "1");
        });
        assertEquals("User with ID 999 not found", exception.getMessage());
    }

    @Test
    public void testAddLoanBookNotFound() {

        when(userService.findUser("1")).thenReturn(testUser);

        when(bookService.findBook("999"))
                .thenThrow(new NoSuchElementException("Book with ID 999 not found"));

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            loanService.addLoan("1", "999");
        });
        assertEquals("Book with ID 999 not found", exception.getMessage());
    }

    @Test
    public void testAddLoanSuccess() {
        when(userService.findUser("1")).thenReturn(testUser);
        when(bookService.findBook("1")).thenReturn(testBook);

        loanService.addLoan("1", "1");

        Loan loan = loanService.getLoanByUser("1");

        assertNotNull(loan);
        assertEquals(testUser, loan.getUser());
        assertEquals(testBook, loan.getBook());
    }

    @Test
    public void testGetLoanByUserNoLoan() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            loanService.getLoanByUser("2");
        });
        assertEquals("El usuario con el id 2 no tiene prestamos", exception.getMessage());
    }
}
