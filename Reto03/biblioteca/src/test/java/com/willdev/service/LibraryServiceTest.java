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
import java.io.InputStream;
import java.util.Scanner;
import com.willdev.exception.NoSuchElementException;
import com.willdev.exception.UserNotFoundException;
import com.willdev.model.Book;

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
    void testMenuBookRegisterBook() {
        String input = "1\n1\nLibro1\nAutor1\n7\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        when(bookService.findBook("1")).thenThrow(new NoSuchElementException("Libro no existe"));

        assertDoesNotThrow(() -> libraryService.menuBook());
        verify(bookService).addBook("1", "Libro1", "Autor1");
    }

    @Test
    void testMenuBookUpdateBookTitle() {
        String input = "4\n1\nNuevoTitulo\n7\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Book mockBook = new Book("1", "TituloViejo", "Autor");
        when(bookService.findBook("1")).thenReturn(mockBook);

        libraryService.menuBook();
        verify(bookService).updateBookTitle("1", "NuevoTitulo");
    }

    @Test
    void testRegisterBookSuccess() {
        Scanner scanner = new Scanner("1\nLibro1\nAutor1");
        when(bookService.findBook("1")).thenThrow(new NoSuchElementException("Libro no existe"));

        libraryService.RegisterBook(scanner);
        verify(bookService).addBook("1", "Libro1", "Autor1");
    }

    @Test
    void testRegisterBookAlreadyExists() {
        Scanner scanner = new Scanner("1\nLibro1\nAutor1");
        when(bookService.findBook("1")).thenReturn(new Book("1", "Libro1", "Autor1"));

        libraryService.RegisterBook(scanner);
        verify(bookService, never()).addBook(any(), any(), any());
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
    void testMenuLibrary_ExitOption() {
        // Simular entrada: 4 (Salir)
        String input = "4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        assertDoesNotThrow(() -> libraryService.menuLibrary());
    }

    @Test
    void testMenuLibrary_UserMenuOption() {
        // Simular entrada: 1 (Menú usuarios) + 6 (Salir)
        String input = "1\n6\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        libraryService.menuLibrary();
        
        // Verificar que se entró al menú de usuarios
        verify(userService, atLeastOnce()).findUser(anyString());
    }

    @Test
    void testMenuLibrary_BookMenuOption() {
        // Simular entrada: 2 (Menú libros) + 7 (Salir)
        String input = "2\n7\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        libraryService.menuLibrary();
        
        // Verificar que se entró al menú de libros
        verify(bookService, atLeastOnce()).findBook(anyString());
    }

    @Test
    void testMenuLibrary_LoanMenuOption() {
        // Simular entrada: 3 (Menú préstamos) + 5 (Salir)
        String input = "3\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        libraryService.menuLibrary();
        
        // Verificar que se entró al menú de préstamos
        verify(loanService, atLeastOnce()).addLoan(anyString(), anyString());
    }

    @Test
    void testMenuLibrary_InvalidOption() {
        // Simular entrada: 99 (Inválido) + 4 (Salir)
        String input = "99\n4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        libraryService.menuLibrary();
        
        // Verificar que se manejó la opción inválida
        verify(bookService, never()).findBook(anyString());
    }




}
