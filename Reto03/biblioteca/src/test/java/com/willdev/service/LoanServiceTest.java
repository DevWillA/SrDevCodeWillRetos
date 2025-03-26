package com.willdev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.List;

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
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(bookService.findBook("1")).thenReturn(new Book("1", "Java Programming", "John Doe"));
        when(userService.findUser("1")).thenReturn(new User("1", "Alice", "Alice@gmail.com"));
        when(userService.findUser("2")).thenReturn(new User("2", "Alice2", "Alice2@gmail.com"));
    }

    private void assertLoanDetails(Loan loan, String expectedUserName, String expectedBookTitle) {
        assertNotNull(loan);
        assertEquals(expectedUserName, loan.getUser().getName());
        assertEquals(expectedBookTitle, loan.getBook().getTitle());
    }

    @Test
    void testAddLoan() {
        assertDoesNotThrow(() -> loanService.addLoan("1", "1"));
        assertLoanDetails(loanService.getLoanByUser("1"), "Alice", "Java Programming");
    }

    @Test
    void testAddLoanUserNotFound() {
        when(userService.findUser("999")).thenThrow(new UserNotFoundException("El usuario con el id 999 no existe"));
        assertThrows(UserNotFoundException.class, () -> loanService.addLoan("999", "1"),
                "El usuario con el id 999 no existe");
    }

    @Test
    void testAddLoanAlreadyLoanedBySameUser() {
        loanService.addLoan("1", "1");
        assertThrows(IllegalStateException.class, () -> loanService.addLoan("1", "1"),
                "El libro 'Java Programming' ya está prestado por el usuario Alice (ID: 1)");
    }

    @Test
    void testAddLoanExceptionHandling() {
        LoanService spyLoanService = spy(new LoanService(bookService, userService));
        doThrow(new NoSuchElementException("El libro no existe")).when(bookService).findBook("999");
        assertThrows(NoSuchElementException.class, () -> spyLoanService.addLoan("1", "999"), "El libro no existe");
    }

    @Test
    void testAddLoanWhenBookAlreadyLoanedShouldThrowException() {
        User mockUser1 = new User("1", "Alice", "alice@example.com");
        User mockUser2 = new User("2", "Bob", "bob@example.com");
        Book mockBook = new Book("1", "Java Programming", "Author");

        when(userService.findUser("1")).thenReturn(mockUser1);
        when(userService.findUser("2")).thenReturn(mockUser2);
        when(bookService.findBook("1")).thenReturn(mockBook);

        loanService.addLoan("1", "1");
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> loanService.addLoan("2", "1"));

        assertEquals("El libro 'Java Programming' ya está prestado por el usuario Alice (ID: 1)",
                exception.getMessage());
    }

    @Test
    void testAddLoanWhenSameUserBorrowsAgainShouldThrowException() {
        User mockUser = new User("1", "Alice", "alice@example.com");
        Book mockBook = new Book("1", "Java Programming", "Author");

        when(userService.findUser("1")).thenReturn(mockUser);
        when(bookService.findBook("1")).thenReturn(mockBook);

        loanService.addLoan("1", "1");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> loanService.addLoan("1", "1"));

        assertEquals("El libro 'Java Programming' ya está prestado por el usuario Alice (ID: 1)",
                exception.getMessage());
    }

    @Test
    void testGetLoansByUser() {
        loanService.addLoan("1", "1");
        assertLoanDetails(loanService.getLoanByUser("1"), "Alice", "Java Programming");
    }

    @Test
    void testGetLoansByUserNoLoans() {
        assertThrows(NoSuchElementException.class, () -> loanService.getLoanByUser("999"),
                "El usuario con el id 999 no tiene prestamos");
    }

    @Test
    void testGetLoanByUserWhenSingleLoanExistsShouldReturnLoan() {
        User mockUser = new User("1", "Alice", "alice@example.com");
        Book mockBook = new Book("1", "Java Programming", "Author");

        when(userService.findUser("1")).thenReturn(mockUser);
        when(bookService.findBook("1")).thenReturn(mockBook);

        loanService.addLoan("1", "1");

        Loan foundLoan = loanService.getLoanByUser("1");

        assertNotNull(foundLoan);
        assertEquals("1", foundLoan.getUser().getId());
        assertEquals("Alice", foundLoan.getUser().getName());
        assertEquals("Java Programming", foundLoan.getBook().getTitle());
    }

    @Test
    void testGetLoanByUserWhenMultipleLoansExistShouldReturnFirstMatch() {
        User mockUser = new User("1", "Alice", "alice@example.com");
        Book mockBook1 = new Book("1", "Java Programming", "Author");
        Book mockBook2 = new Book("2", "Python Basics", "Author");

        when(userService.findUser("1")).thenReturn(mockUser);
        when(bookService.findBook("1")).thenReturn(mockBook1);
        when(bookService.findBook("2")).thenReturn(mockBook2);

        loanService.addLoan("1", "1");
        loanService.addLoan("1", "2");

        Loan foundLoan = loanService.getLoanByUser("1");

        assertNotNull(foundLoan);
        assertEquals("1", foundLoan.getUser().getId());
    }

    @Test
    void testGetLoanByUserWhenNoLoansExistShouldThrowException() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> loanService.getLoanByUser("1"));

        assertEquals("El usuario con el id 1 no tiene prestamos", exception.getMessage());
    }

    @Test
    void testGetLoanByUserWhenUserHasNoLoansShouldThrowException() {
        User mockUser1 = new User("1", "Alice", "alice@example.com");
        User mockUser2 = new User("2", "Bob", "bob@example.com");
        Book mockBook = new Book("1", "Java Programming", "Author");

        when(userService.findUser("1")).thenReturn(mockUser1);
        when(userService.findUser("2")).thenReturn(mockUser2);
        when(bookService.findBook("1")).thenReturn(mockBook);

        loanService.addLoan("1", "1");
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> loanService.getLoanByUser("2"));

        assertEquals("El usuario con el id 2 no tiene prestamos", exception.getMessage());
    }

    @Test
    void testReturnLoan() {
        loanService.addLoan("1", "1");
        loanService.returnBook("1", "1");
        assertThrows(NoSuchElementException.class, () -> loanService.getLoanByUser("1"),
                "El usuario con el id 1 no tiene prestamos");
    }

    @Test
    void testReturnLoanNotFound() {
        assertThrows(NoSuchElementException.class, () -> loanService.returnBook("999", "1"),
                "El usuario con el id 999 no tiene prestamos");
    }

    @Test
    void testReturnLoanEmptyUserId() {
        assertThrows(NoSuchElementException.class, () -> loanService.returnBook("", "1"),
                "El usuario con el id  no tiene prestamos");
    }

    @Test
    void testReturnLoanEmptyBookId() {
        assertThrows(NoSuchElementException.class, () -> loanService.returnBook("1", ""),
                "El usuario con el id 1 no tiene prestamos");
    }

    @Test
    void testReturnBookWhenLoanExistsShouldRemoveAndReturnLoan() {
        User mockUser = new User("1", "Alice", "alice@example.com");
        Book mockBook = new Book("1", "Java Programming", "Author");

        when(userService.findUser("1")).thenReturn(mockUser);
        when(bookService.findBook("1")).thenReturn(mockBook);

        loanService.addLoan("1", "1"); 

        Loan returnedLoan = loanService.returnBook("1", "1");

        assertNotNull(returnedLoan);
        assertEquals("1", returnedLoan.getUser().getId());
        assertEquals("1", returnedLoan.getBook().getId());

        assertThrows(NoSuchElementException.class,
                () -> loanService.getLoanByUser("1"));
    }

    @Test
    void testReturnBookWhenUserHasMultipleLoansShouldRemoveCorrectOne() {
        User mockUser = new User("1", "Alice", "alice@example.com");
        Book mockBook1 = new Book("1", "Java Programming", "Author");
        Book mockBook2 = new Book("2", "Python Basics", "Author");

        when(userService.findUser("1")).thenReturn(mockUser);
        when(bookService.findBook("1")).thenReturn(mockBook1);
        when(bookService.findBook("2")).thenReturn(mockBook2);

        loanService.addLoan("1", "1"); 
        loanService.addLoan("1", "2"); 
        Loan returnedLoan = loanService.returnBook("1", "1");

        assertNotNull(returnedLoan);
        assertEquals("1", returnedLoan.getBook().getId());

        Loan remainingLoan = loanService.getLoanByUser("1");
        assertEquals("2", remainingLoan.getBook().getId());
    }

    @Test
    void testReturnBookWhenLoanNotExistsShouldThrowException() {
        User mockUser = new User("1", "Alice", "alice@example.com");
        Book mockBook = new Book("1", "Java Programming", "Author");

        when(userService.findUser("1")).thenReturn(mockUser);
        when(bookService.findBook("1")).thenReturn(mockBook);

        loanService.addLoan("1", "1");
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> loanService.returnBook("1", "2")); 

        assertEquals("El usuario con el id 1 no tiene prestamos", exception.getMessage());
    }



    @Test
    void testReturnBookWithNullIdsShouldThrowException() {
        assertThrows(NoSuchElementException.class,
                () -> loanService.returnBook(null, "1"));

        assertThrows(NoSuchElementException.class,
                () -> loanService.returnBook("1", null));
    }

    @Test
    void testGetLoanByBook() {
        loanService.addLoan("1", "1");
        assertLoanDetails(loanService.getLoanByBook("1"), "Alice", "Java Programming");
    }

    @Test
    void testGetLoanByBookNotFound() {
        assertThrows(NoSuchElementException.class, () -> loanService.getLoanByBook("999"),
                "El Libro con el id 999 no tiene prestamos");
    }

    @Test
    void testGetLoanByBookWhenLoanExistsShouldReturnLoan() {
        User mockUser = new User("1", "Alice", "alice@example.com");
        Book mockBook = new Book("1", "Java Programming", "Author");

        when(userService.findUser("1")).thenReturn(mockUser);
        when(bookService.findBook("1")).thenReturn(mockBook);

        loanService.addLoan("1", "1");

        Loan foundLoan = loanService.getLoanByBook("1");

        assertNotNull(foundLoan);
        assertEquals("Java Programming", foundLoan.getBook().getTitle());
        assertEquals("Alice", foundLoan.getUser().getName());

        assertEquals("1", foundLoan.getBook().getId());
    }

    @Test
    void testGetLoanByBookWhenMultipleLoansExistShouldFindCorrectOne() {
        User mockUser1 = new User("1", "Alice", "alice@example.com");
        User mockUser2 = new User("2", "Bob", "bob@example.com");
        Book mockBook1 = new Book("1", "Java Programming", "Author");
        Book mockBook2 = new Book("2", "Python Basics", "Author");

        when(userService.findUser("1")).thenReturn(mockUser1);
        when(userService.findUser("2")).thenReturn(mockUser2);
        when(bookService.findBook("1")).thenReturn(mockBook1);
        when(bookService.findBook("2")).thenReturn(mockBook2);

        loanService.addLoan("1", "1");
        loanService.addLoan("2", "2");

        Loan foundLoan = loanService.getLoanByBook("2");

        assertNotNull(foundLoan);
        assertEquals("Python Basics", foundLoan.getBook().getTitle());
        assertEquals("Bob", foundLoan.getUser().getName());
    }

    @Test
    void testGetLoanByBookWhenNoLoansExistShouldThrowException() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> loanService.getLoanByBook("1"));

        assertEquals("El Libro con el id 1 no tiene prestamos", exception.getMessage());
    }

    @Test
    void testGetLoanByBookWhenBookNotLoanedShouldThrowException() {
        User mockUser = new User("1", "Alice", "alice@example.com");
        Book mockBook1 = new Book("1", "Java Programming", "Author");
        Book mockBook2 = new Book("2", "Python Basics", "Author");

        when(userService.findUser("1")).thenReturn(mockUser);
        when(bookService.findBook("1")).thenReturn(mockBook1);
        when(bookService.findBook("2")).thenReturn(mockBook2);

        loanService.addLoan("1", "1");

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> loanService.getLoanByBook("2"));

        assertEquals("El Libro con el id 2 no tiene prestamos", exception.getMessage());
    }

}
