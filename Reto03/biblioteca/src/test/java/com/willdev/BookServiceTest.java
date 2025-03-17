package com.willdev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookServiceTest {
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }

    @Test
    void testAddBook() {
        bookService.addBook("1", "Java Programming", "John Doe");
        Books book = bookService.findBook("1");
        assertNotNull(book);
        assertEquals("Java Programming", book.getTitle());
        assertEquals("John Doe", book.getOwner());
    }

    @Test
    void testAddAndFindBook_Success() {
        bookService.addBook("1", "Java Programming", "John Doe");
        Books book = bookService.findBook("1");
        assertNotNull(book);
        assertEquals("Java Programming", book.getTitle());
    }

    @Test
    void testFindBook_NotFound() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            bookService.findBook("999");
        });
        assertEquals("El libro con el id 999 no existe", exception.getMessage());
    }

    @Test
    void testFindBookById() {
        bookService.addBook("2", "Python Basics", "Alice Smith");
        Books book = bookService.findBook("2");
        assertNotNull(book);
        assertEquals("Python Basics", book.getTitle());
    }

    @Test
    void testFindBookById_NotFound() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            bookService.findBook("999");
        });
        assertEquals("El libro con el id 999 no existe", exception.getMessage());
    }
}
