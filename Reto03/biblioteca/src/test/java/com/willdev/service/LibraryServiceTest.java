package com.willdev.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.willdev.exception.NoSuchElementException;
import com.willdev.exception.UserNotFoundException;
import com.willdev.model.Book;
import com.willdev.model.Loan;
import com.willdev.model.User;

public class LibraryServiceTest {

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks_ReturnsListOfBooks() {
        List<Book> mockBooks = Arrays.asList(
                new Book("1", "Libro 1", "Autor 1"),
                new Book("2", "Libro 2", "Autor 2"));
        when(bookService.getAllBooks()).thenReturn(mockBooks);

        List<Book> result = libraryService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Libro 1", result.get(0).getTitle());
        assertEquals("Autor 2", result.get(1).getOwner());

        verify(bookService, times(1)).getAllBooks();
    }

    // Menu de libros
    @Test
    void testMenuBookRegisterBook() {
        String input = "1\n1\nLibro1\nAutor1\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(bookService.findBook("1")).thenThrow(new NoSuchElementException("Libro no existe"));

        libraryService.menuBook();
        verify(bookService).addBook("1", "Libro1", "Autor1");
    }

    @Test
    void testMenuBookDeleteBook() {
        String input = "5\n1\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(bookService.findBook("1")).thenThrow(new NoSuchElementException("Libro no existe"));

        libraryService.menuBook();
        verify(bookService).deleteBook("1");

    }

    @Test
    void testMenuBookDeleteBookNotFoundBook() {
        String input = "5\n1\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        doThrow(new NoSuchElementException("Libro no existe"))
                .when(bookService).deleteBook("1");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.menuBook();

        verify(bookService).deleteBook("1");

        String output = outputStream.toString();
        assertTrue(output.contains("El libro no existe"));

        assertFalse(output.contains("Libro eliminado con éxito"));
    }

    @Test
    void testMenuBookSeeBook() {
        String input = "2\n1\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Book mockBook = new Book("1", "Libro Existente", "Autor Conocido");
        when(bookService.findBook("1")).thenReturn(mockBook);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.menuBook();

        verify(bookService).findBook("1");

        String output = outputStream.toString();
        assertTrue(output.contains("Libro encontrado:"));
        assertFalse(output.contains("Libro Existente"));
        assertFalse(output.contains("El libro no existe"));
    }

    @Test
    void testMenuBookSeeBookNotFound() {
        String input = "2\n1\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(bookService.findBook("1")).thenThrow(new NoSuchElementException("Libro no existe"));

        libraryService.menuBook();
        verify(bookService).findBook("1");
    }

    @Test
    void testMenuBookUpdateBookTitle() {

        String input = "3\n1\nNuevoTitulo\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Book mockBook = new Book("1", "TituloViejo", "Autor");
        when(bookService.findBook("1")).thenReturn(mockBook);

        libraryService.menuBook();
        verify(bookService).updateBookTitle("1", "NuevoTitulo");
    }

    @Test
    void testMenuBookUpdateTitleBookNotFound() {
        String input = "3\n1\nNuevoTitulo\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        doThrow(new NoSuchElementException("Libro no existe"))
                .when(bookService).updateBookTitle("1", "NuevoTitulo");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.menuBook();

        verify(bookService).updateBookTitle("1", "NuevoTitulo");

        String output = outputStream.toString();
        assertTrue(output.contains("El libro no existe"));
        assertFalse(output.contains("actualizado con éxito"));
    }

    @Test
    void testMenuBookUpdateBookOwner() {

        String input = "4\n1\nAutorNuevo\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Book mockBook = new Book("1", "Titulo", "AutorViejo");
        when(bookService.findBook("1")).thenReturn(mockBook);

        libraryService.menuBook();
        verify(bookService).updateBookOwner("1", "AutorNuevo");
    }

    @Test
    void testMenuBookUpdateOwnerBookNotFound() {
        String input = "4\n1\nNuevoAutor\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        doThrow(new NoSuchElementException("Libro no existe"))
                .when(bookService).updateBookOwner("1", "NuevoAutor");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.menuBook();

        verify(bookService).updateBookOwner("1", "NuevoAutor");

        String output = outputStream.toString();
        assertTrue(output.contains("El libro no existe"));
        assertFalse(output.contains("actualizado con éxito"));
    }

    @Test
    void testMenuBookRegisterBookAlreadyExists() {
        Scanner scanner = new Scanner("1\nLibro1\nAutor1");
        when(bookService.findBook("1")).thenReturn(new Book("1", "Libro1", "Autor1"));

        libraryService.RegisterBook(scanner);
        verify(bookService, never()).addBook(any(), any(), any());
    }

    @Test
    void testMenuBookInvalidOption() {
        String input = "99\n6\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        libraryService.menuBook();

        verify(bookService, never()).findBook(anyString());
    }

    @Test
    void testRegisterBookWhenBookExistsShouldShowErrorMessage() {
        String input = "1\nLibroExistente\nAutorExistente\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(bookService.findBook("1")).thenReturn(new Book("1", "LibroExistente", "AutorExistente"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.RegisterBook(new Scanner(System.in));

        verify(bookService).findBook("1");
        verify(bookService, never()).addBook(any(), any(), any());

        String output = outputStream.toString();
        assertTrue(output.contains("El libro con el id 1 ya existe"));
    }

    @Test
    void testRegisterBookWhenServiceThrowsExceptionShouldHandleGracefully() {
        String input = "3\nLibroError\nAutorError\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(bookService.findBook("3")).thenThrow(new RuntimeException("Error de base de datos"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.RegisterBook(new Scanner(System.in));

        String output = outputStream.toString();
        assertFalse(output.contains("Registrando el libro"));
    }

    // Menu de Usuarios

    @Test
    void testMenuUserRegisterUser() {
        String input = "1\n1\nUser1\nEmail1\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("1")).thenThrow(new UserNotFoundException("User no existe"));

        libraryService.menuUser();
        verify(userService).addUser("1", "User1", "Email1");
    }

    @Test
    void testMenuUserDeleteUser() {
        String input = "5\n1\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("1")).thenThrow(new UserNotFoundException("User no existe"));

        libraryService.menuUser();
        verify(userService).deleteUser("1");
    }

    @Test
    void testMenuUserDeleteUserNotExist() {
        String input = "5\n2\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        doThrow(new NoSuchElementException("El usuario no existe"))
                .when(userService).deleteUser("2");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.menuUser();

        verify(userService).deleteUser("2");

        String output = outputStream.toString();
        assertTrue(output.contains("El usuario no existe"));
    }

    @Test
    void testMenuUserGetUser() {
        String input = "4\n1\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        libraryService.menuUser();
        verify(userService).findUser("1");
    }

    @Test
    void testMenuUserGetUserNotExist() {
        String input = "4\n2\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        doThrow(new NoSuchElementException("El usuario no existe"))
                .when(userService).findUser("2");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.menuUser();

        verify(userService).findUser("2");

        String output = outputStream.toString();
        assertTrue(output.contains("El usuario no existe"));
    }

    @Test
    void testMenuUserUpdateNameUser() {

        String input = "2\n1\nNuevoUser\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        User mockUser = new User("1", "TituloViejo", "Autor");
        when(userService.findUser("1")).thenReturn(mockUser);

        libraryService.menuUser();
        verify(userService).updateUserName("1", "NuevoUser");
    }

    @Test
    void testMenuUserUpdateNameUserNotExist() {
        String input = "2\n2\nNameNuevo\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("2")).thenThrow(new NoSuchElementException("El usuario no existe"));

        doThrow(new NoSuchElementException("El usuario no existe"))
                .when(userService).updateUserName("2", "NameNuevo");

        libraryService.menuUser();

        verify(userService).updateUserName("2", "NameNuevo");
    }

    @Test
    void testMenuUserUpdateEmailUser() {

        String input = "3\n1\nEmailNuevo\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        User mockUser = new User("1", "User", "EmailViejo");
        when(userService.findUser("1")).thenReturn(mockUser);

        libraryService.menuUser();
        verify(userService).updateUserEmail("1", "EmailNuevo");
    }

    @Test
    void testMenuUserUpdateEmailUserNotExist() {
        String input = "3\n2\nEmailNuevo\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("2")).thenThrow(new NoSuchElementException("El usuario no existe"));

        doThrow(new NoSuchElementException("El usuario no existe"))
                .when(userService).updateUserEmail("2", "EmailNuevo");

        libraryService.menuUser();

        verify(userService).updateUserEmail("2", "EmailNuevo");
    }

    @Test
    void testMenuUserRegisterUserAlreadyExists() {
        Scanner scanner = new Scanner("1\nUser1\nEmail1");
        when(userService.findUser("1")).thenReturn(new User("1", "User1", "Email1"));

        libraryService.RegisterUser(scanner);
        verify(userService, never()).addUser(any(), any(), any());
    }

    @Test
    void testMenuUserInvalidOption() {
        String input = "99\n6\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        libraryService.menuUser();

        verify(userService, never()).findUser(anyString());
    }

    @Test
    void testRegisterUserWhenUserExistsShouldShowErrorMessage() {
        String input = "1\nUsuarioExistente\ncorreo@existente.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("1")).thenReturn(new User("1", "UsuarioExistente", "correo@existente.com"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.RegisterUser(new Scanner(System.in));

        verify(userService).findUser("1");
        verify(userService, never()).addUser(any(), any(), any());

        String output = outputStream.toString();
        assertTrue(output.contains("El Usuario con el id 1 ya existe"));
    }

    @Test
    void testRegisterUserWhenServiceThrowsExceptionShouldHandleGracefully() {
        String input = "3\nUsuarioError\ncorreo@error.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("3")).thenThrow(new RuntimeException("Error de base de datos"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.RegisterUser(new Scanner(System.in));

        String output = outputStream.toString();
        assertFalse(output.contains("Registrando el usuario"));
    }

    // Menu de Prestamos

    @Test
    void testMenuLoanRegisterLoan() {
        String input = "1\n1\n1\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("1")).thenReturn(new User("1", "User1", "Email1"));
        when(bookService.findBook("1")).thenReturn(new Book("1", "Libro1", "Autor1"));

        libraryService.menuLoan();
        verify(loanService).addLoan("1", "1");
    }

    @Test
    void testMenuLoanReturnBook() {
        String input = "2\n1\n1\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(loanService.getLoanByUser("1"))
                .thenReturn(new Loan(new User("1", "User1", "Email1"), new Book("1", "Libro1", "Autor1")));

        libraryService.menuLoan();
        verify(loanService).returnBook("1", "1");
    }

    @Test
    void testMenuLoanReturnBookNotLoans() {
        String input = "2\n1\n1\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        doThrow(new NoSuchElementException("El usuario no tiene préstamos"))
                .when(loanService).returnBook("1", "1");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.menuLoan();

        verify(loanService).returnBook("1", "1");

        String output = outputStream.toString();
        assertTrue(output.contains("El usuario no tiene prestamos"));
        assertFalse(output.contains("Regreso registrado con éxito"));
    }

    @Test
    void testMenuLoanGetLoanbyUser() {
        String input = "3\n1\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("1")).thenReturn(new User("1", "User1", "Email1"));
        when(bookService.findBook("1")).thenReturn(new Book("1", "Libro1", "Autor1"));
        when(loanService.getLoanByUser("1"))
                .thenReturn(new Loan(new User("1", "User1", "Email1"), new Book("1", "Libro1", "Autor1")));

        libraryService.menuLoan();
        verify(loanService).getLoanByUser("1");
    }

    @Test
    void testMenuLoanGetLoanByBookNotFoundUser() {
        String input = "3\n2\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        when(loanService.getLoanByUser("2"))
                .thenThrow(new NoSuchElementException("El Usuario no tiene préstamos"));

        doThrow(new NoSuchElementException("El User no existe"))
                .when(loanService).getLoanByUser("2");

        libraryService.menuLoan();

        verify(loanService).getLoanByUser("2");

    }

    @Test
    void testMenuLoanGetLoanbyBook() {

        String input = "4\n1\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("1")).thenReturn(new User("1", "User1", "Email1"));
        when(bookService.findBook("1")).thenReturn(new Book("1", "Libro1", "Autor1"));
        when(loanService.getLoanByUser("1"))
                .thenReturn(new Loan(new User("1", "User1", "Email1"), new Book("1", "Libro1", "Autor1")));

        libraryService.menuLoan();
        verify(loanService).getLoanByBook("1");
    }

    @Test
    void testMenuLoanGetLoanByBookNotFoundBook() {
        String input = "4\n2\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        when(loanService.getLoanByBook("2"))
                .thenThrow(new NoSuchElementException("El libro no tiene préstamos"));

        doThrow(new NoSuchElementException("El Libro no existe"))
                .when(loanService).getLoanByBook("2");

        libraryService.menuLoan();

        verify(loanService).getLoanByBook("2");

    }

    @Test
    void testMenuLoanInvalidOption() {
        String input = "99\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        libraryService.menuLoan();

        verify(loanService, never()).getLoanByBook(anyString());
    }

    @Test
    void testGetUserNotFound() {
        Scanner scanner = new Scanner("999");
        when(userService.findUser("999"))
                .thenThrow(new UserNotFoundException("Usuario no existe"));

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> libraryService.getUser(scanner));

        assertEquals("Usuario no existe", exception.getMessage());

        verify(userService).findUser("999");
    }

    @Test
    void testMenuLibraryExitOption() {
        String input = "4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertDoesNotThrow(() -> libraryService.menuLibrary());
    }

    @Test
    void testMenuLibraryInvalidOption() {
        String input = "99\n4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        libraryService.menuLibrary();

        verify(bookService, never()).findBook(anyString());
    }

    @Test
    void testMenuLibraryOption4ShouldExit() {
        String input = "4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.menuLibrary();

        String output = outputStream.toString();
        assertTrue(output.contains("Cerrando sistema..."));
    }

    @Test
    void testMenuLibraryInvalidOptionShouldShowErrorMessage() {
        String input = "99\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.menuLibrary();

        String output = outputStream.toString();
        assertTrue(output.contains("Opción no válida"));
    }

    @Test
    void testMakeLoanSuccessfulLoanShouldRegisterCorrectly() {
        String input = "1\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("1")).thenReturn(new User("1", "Alice", "alice@example.com"));
        when(bookService.findBook("1")).thenReturn(new Book("1", "Java Programming", "Author"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.MakeLoan(new Scanner(System.in));

        verify(loanService).addLoan("1", "1");
        String output = outputStream.toString();
        assertTrue(output.contains("Prestamo registrado con éxito"));
    }

    @Test
    void testMakeLoanWhenBookNotFoundShouldHandleNoSuchElementException() {
        String input = "1\n99\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("1")).thenReturn(new User("1", "Alice", "alice@example.com"));
        when(bookService.findBook("99")).thenThrow(new NoSuchElementException("Libro no encontrado"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.MakeLoan(new Scanner(System.in));

        String output = outputStream.toString();
        assertFalse(output.contains("El libro no existe"));
        assertFalse(output.contains("Libro no encontrado"));
    }

    @Test
    void testMakeLoanWhenUserNotFoundShouldHandleUserNotFoundException() {
        String input = "99\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("99")).thenThrow(new UserNotFoundException("Usuario no encontrado"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.MakeLoan(new Scanner(System.in));

        String output = outputStream.toString();
        assertFalse(output.contains("El usuario no existe"));
        assertFalse(output.contains("Usuario no encontrado"));
    }

    @Test
    void testMakeLoanWhenBookAlreadyLoanedShouldHandleIllegalStateException() {
        String input = "1\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(userService.findUser("1")).thenReturn(new User("1", "Alice", "alice@example.com"));
        when(bookService.findBook("1")).thenReturn(new Book("1", "Java Programming", "Author"));
        doThrow(new IllegalStateException("Libro ya prestado")).when(loanService).addLoan("1", "1");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        libraryService.MakeLoan(new Scanner(System.in));

        String output = outputStream.toString();
        assertFalse(output.contains("El libro ya esta prestado"));
        assertTrue(output.contains("Libro ya prestado"));
    }

}
