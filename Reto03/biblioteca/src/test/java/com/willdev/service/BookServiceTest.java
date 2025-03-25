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
        bookService.addBook("1", "Java", "John");
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

    @Test
    void testDeleteBookSuccess() {
        bookService.addBook("1", "Java", "John");
        bookService.deleteBook("1");
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            bookService.findBook("1");
        });
        assertEquals("El libro con el id 1 no existe", exception.getMessage());
    }

    @Test
    void testDeleteBookNotFound() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            bookService.deleteBook("999");
        });
        assertEquals("El libro con el id 999 no existe, no se puede eliminar", exception.getMessage());
    }

    @Test
    void testUpdateBookOwnerSuccess() {
        bookService.addBook("1", "Java", "John");
        bookService.updateBookOwner("1", "Alice");
        Book book = bookService.findBook("1");
        assertNotNull(book);
        assertEquals("Alice", book.getOwner());
    }

    @Test
    void testUpdateBookOwnerNotFound() {
        bookService.addBook("1", "Java", "John");
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            bookService.updateBookOwner("2", "Alice");
        });
        assertEquals("El libro con el id 2 no existe, no se puede actualizar autor", exception.getMessage());
    }


    @Test
    void testUpdateBookTitleSuccess() {
        bookService.addBook("1", "Java", "John");
        bookService.updateBookTitle("1", "Java 8");
        Book book = bookService.findBook("1");
        assertNotNull(book);
        assertEquals("Java 8", book.getTitle());
    }

    @Test
    void testUpdateBookTitleNotFound() {
        bookService.addBook("1", "Java", "John");
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            bookService.updateBookTitle("2", "Java 8");
        });
        assertEquals("El libro con el id 2 no existe, no se puede actualizar titulo", exception.getMessage());
    }


    @Test
    void addBook_ShouldAddBookToList() {
        bookService.addBook("1", "Title", "Owner");
        assertEquals(1, bookService.getAllBooks().size());
    }

    @Test
    void findBook_ExistingBook_ShouldReturnBook() {
        bookService.addBook("1", "Title", "Owner");
        Book found = bookService.findBook("1");
        assertEquals("1", found.getId());
    }

    @Test
    void findBook_NonExistingBook_ShouldThrowException() {
        assertThrows(NoSuchElementException.class, () -> bookService.findBook("99"));
    }

    @Test
    void deleteBook_ExistingBook_ShouldRemoveBook() {
        bookService.addBook("1", "Title", "Owner");
        bookService.deleteBook("1");
        assertEquals(0, bookService.getAllBooks().size());
    }

}
