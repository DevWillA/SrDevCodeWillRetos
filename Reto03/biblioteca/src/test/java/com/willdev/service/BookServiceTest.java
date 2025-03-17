package com.willdev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.willdev.exception.NoSuchElementException;
import com.willdev.model.Book;

public class BookServiceTest {
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }

    @Test
    void testAddBook() {
        // Given - When
        bookService.addBook("1", "Java", "John");
        // Then
        Book book = bookService.findBook("1");
        assertNotNull(book);
        assertEquals("Java", book.getTitle());
        assertEquals("John", book.getOwner());
    }

    @Test
    void testAddAndFindBook_Success() {
        bookService.addBook("1", "Java", "John");
        Book book = bookService.findBook("1");
        assertNotNull(book);
        assertEquals("Java", book.getTitle());
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
        bookService.addBook("2", "Python", "Alice");
        Book book = bookService.findBook("2");
        assertNotNull(book);
        assertEquals("Python", book.getTitle());
    }

    @Test
    void testFindBookById_NotFound() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            bookService.findBook("999");
        });
        assertEquals("El libro con el id 999 no existe", exception.getMessage());
    }
}
