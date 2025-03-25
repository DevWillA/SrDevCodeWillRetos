package com.willdev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.willdev.exception.NoSuchElementException;
import com.willdev.exception.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.willdev.model.Book;
import com.willdev.model.Loan;
import com.willdev.model.User;

public class LoanServiceTest {

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoanService loanService; // Inyecta los mocks en LoanService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    
        when(bookService.findBook("1")).thenReturn(new Book("1", "Java Programming", "John Doe"));
        when(userService.findUser("1")).thenReturn(new User("1", "Alice", "Alice@gmail.com"));
        when(userService.findUser("2")).thenReturn(new User("2", "Alice2", "Alice2@gmail.com"));
    }

    @Test
    void testAddLoan() {
        assertDoesNotThrow(() -> loanService.addLoan("1", "1"));
        Loan loan = loanService.getLoanByUser("1");
        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());
    }

    @Test
    void testAddLoanSuccess() {
        assertDoesNotThrow(() -> loanService.addLoan("1", "1"));
        Loan loan = loanService.getLoanByUser("1");
        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());

    }

    @Test
    void testGetLoansByUser() {
        loanService.addLoan("1", "1");
        Loan loan = loanService.getLoanByUser("1");

        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());
    }

    @Test
    void testGetLoansByUserNoLoans() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> { // Usa la excepción correcta
            loanService.getLoanByUser("999");
        });

        assertEquals("El usuario con el id 999 no tiene prestamos", exception.getMessage()); // Verifica mensaje
    }

    @Test
    void testGetLoansByUserNotFound() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> { // Usa la excepción correcta
            loanService.getLoanByUser("999");
        });

        assertEquals("El usuario con el id 999 no tiene prestamos", exception.getMessage()); // Verifica mensaje
    }

    @Test
    void testReturnLoan() {
        loanService.addLoan("1", "1");
        loanService.returBook("1", "1");
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            loanService.getLoanByUser("1");
        });
        assertEquals("El usuario con el id 1 no tiene prestamos", exception.getMessage());
    }

    @Test
    void testReturnLoanNotFound() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            loanService.returBook("999", "1");
        });
        assertEquals("El usuario con el id 999 no tiene prestamos", exception.getMessage());
    }

    @Test
    void testGetLoanbyBook() {
        loanService.addLoan("1", "1");
        Loan loan = loanService.getLoanByBook("1");
        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());
    }

    @Test
    void testGetLoanbyBookNotFound() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            loanService.getLoanByBook("999");
        });
        assertEquals("El Libro con el id 999 no tiene prestamos", exception.getMessage());
    }

    @Test
    void testAddLoan_UserNotFound() {
        when(userService.findUser("999")).thenThrow(new UserNotFoundException("El usuario con el id 999 no existe"));
    
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            loanService.addLoan("999", "1");
        });
    
        assertEquals("El usuario con el id 999 no existe", exception.getMessage());
    }
    


    @Test
    void testReturnLoan_EmptyUserId() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            loanService.returBook("", "1");
        });
        assertEquals("El usuario con el id  no tiene prestamos", exception.getMessage());
    }

    @Test
    void testAddLoanOcuped() {
        assertDoesNotThrow(() -> loanService.addLoan("1", "1"));
        Loan loan = loanService.getLoanByUser("1");
        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName());
        assertEquals("Java Programming", loan.getBook().getTitle());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            loanService.addLoan("2", "1");
        });
        assertEquals("El libro 'Java Programming' ya está prestado por el usuario Alice (ID: 1)", exception.getMessage());

    }

    @Test
    void testReturnLoanBookNotLoaned() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            loanService.returBook("1", "1"); 
        });
        assertEquals("El usuario con el id 1 no tiene prestamos", exception.getMessage());
    }

    @Test
    void testAddLoanAlreadyLoanedBySameUser() {
        loanService.addLoan("1", "1"); 
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            loanService.addLoan("1", "1"); 
        });
        assertEquals("El libro 'Java Programming' ya está prestado por el usuario Alice (ID: 1)",
                exception.getMessage());
    }

    @Test
    void testGetLoanByUser_EmptyId() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            loanService.getLoanByUser("");
        });
        assertEquals("El usuario con el id  no tiene prestamos", exception.getMessage());
    }

    @Test
    void testGetLoanByUser_NullId() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            loanService.getLoanByUser(null);
        });
        assertEquals("El usuario con el id null no tiene prestamos", exception.getMessage());
    }

    @Test
    void testReturnLoan_EmptyBookId() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            loanService.returBook("1", "");
        });
        assertEquals("El usuario con el id 1 no tiene prestamos", exception.getMessage());
    }

    @Test
    void testAddLoan_NoUsersOrBooks() {
        LoanService emptyLoanService = new LoanService(new BookService(), new UserService());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            emptyLoanService.addLoan("1", "1");
        });
        assertEquals("El usuario con el id 1 no existe", exception.getMessage());
    }

    @Test
    void testReturnLoan_NoUsersOrBooks() {
        LoanService emptyLoanService = new LoanService(new BookService(), new UserService());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            emptyLoanService.returBook("1", "1");
        });
        assertEquals("El usuario con el id 1 no tiene prestamos", exception.getMessage());
    }

    @Test
    void testAddLoanExceptionHandling() {
        LoanService spyLoanService = spy(new LoanService(bookService, userService));

        doThrow(new NoSuchElementException("El libro no existe")).when(bookService).findBook("999");
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            spyLoanService.addLoan("1", "999");
        });
        assertEquals("El libro no existe", exception.getMessage());

        doThrow(new UserNotFoundException("El usuario no existe")).when(userService).findUser("999");
        exception = assertThrows(UserNotFoundException.class, () -> {
            spyLoanService.addLoan("999", "1");
        });
        assertEquals("El usuario no existe", exception.getMessage());
    }

    @Test
    void testReturnLoanMultipleLoans() {
        User user = new User("1", "Will Dev", "sssss");
        Book book1 = new Book("1", "Java Programming");
        Book book2 = new Book("2", "Data Structures");

        when(userService.findUser("1")).thenReturn(user);
        when(bookService.findBook("1")).thenReturn(book1);
        when(bookService.findBook("2")).thenReturn(book2);

        loanService.addLoan("1", "1");
        loanService.addLoan("1", "2");

        Loan returnedLoan = loanService.returBook("1", "1");
        assertNotNull(returnedLoan);
        assertEquals("Java Programming", returnedLoan.getBook().getTitle());

        Loan remainingLoan = loanService.getLoanByUser("1");
        assertNotNull(remainingLoan);
        assertEquals("2", remainingLoan.getBook().getId());
    }

    @Test
    void testGetLoanByUserMultipleLoans() {
        User user = new User("1", "Alice", "dddd"); 
        Book book1 = new Book("1", "Java Programming");
        Book book2 = new Book("2", "Data Structures");

        when(userService.findUser("1")).thenReturn(user); 
        when(bookService.findBook("1")).thenReturn(book1); 
        when(bookService.findBook("2")).thenReturn(book2); 

        loanService.addLoan("1", "1"); 
        loanService.addLoan("1", "2");

        Loan loan = loanService.getLoanByUser("1");
        assertNotNull(loan);
        assertEquals("Alice", loan.getUser().getName()); 
        assertNotNull(loan.getBook()); 
    }

    @Test
    void testAddLoan_BookAlreadyBorrowedByOtherUser() {
        when(userService.findUser("1")).thenReturn(new User("1", "Alice", "alice@example.com"));
        when(userService.findUser("2")).thenReturn(new User("2", "Bob", "bob@example.com"));
        when(bookService.findBook("1")).thenReturn(new Book("1", "Java Programming", "Author"));
    
        loanService.addLoan("1", "1");
    
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            loanService.addLoan("2", "1");
        });
    
        assertEquals(
            "El libro 'Java Programming' ya está prestado por el usuario Alice (ID: 1)",
            exception.getMessage()
        );
    }
    
    
}
