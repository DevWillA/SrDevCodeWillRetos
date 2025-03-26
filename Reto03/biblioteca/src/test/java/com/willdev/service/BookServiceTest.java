package com.willdev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

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
        bookService.addBook("1", "Java Basics", "Alice");
        List<Book> books = bookService.getAllBooks();
        assertEquals(1, books.size());
        assertEquals("Java Basics", books.get(0).getTitle());
    }

    @Test
    void testFindBook() {
        bookService.addBook("1", "Java Basics", "Alice");
        bookService.addBook("2", "Advanced Java", "Bob"); // Libro adicional

        Book foundBook = bookService.findBook("1");
        Book anotherBook = bookService.findBook("2");

        assertNotNull(foundBook);
        assertEquals("Java Basics", foundBook.getTitle());
        assertEquals("Alice", foundBook.getOwner());

        assertNotNull(anotherBook);
        assertEquals("Advanced Java", anotherBook.getTitle());
    }

    @Test
    void testFindBookBookNotExists() {
        bookService.addBook("1", "Java Basics", "Alice");

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> bookService.findBook("99"));

        assertEquals("El libro con el id 99 no existe", exception.getMessage());

        bookService = new BookService();
        assertThrows(NoSuchElementException.class,
                () -> bookService.findBook("1"));
    }

    @Test
    void testDeleteBook() {
        bookService.addBook("1", "Java Basics", "Alice");
        bookService.deleteBook("1");
        assertThrows(NoSuchElementException.class, () -> bookService.findBook("1"));
    }

    @Test
    void testDeleteBookNotFound() {
        assertThrows(NoSuchElementException.class, () -> bookService.deleteBook("99"));
    }

    @Test
    void testDeleteBookWhenBookExistsShouldRemoveBook() {
        bookService.addBook("1", "Java Basics", "Alice");
        bookService.addBook("2", "Advanced Java", "Bob");

        bookService.deleteBook("1");

        assertThrows(NoSuchElementException.class, () -> bookService.findBook("1"));

        Book remainingBook = bookService.findBook("2");
        assertNotNull(remainingBook);
        assertEquals("Advanced Java", remainingBook.getTitle());

        assertEquals(1, bookService.getAllBooks().size());
    }

    @Test
    void testDeleteBookWhenBookNotExistsShouldThrowException() {
        bookService.addBook("1", "Java Basics", "Alice");

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> bookService.deleteBook("99"));

        assertEquals("El libro con el id 99 no existe, no se puede eliminar", exception.getMessage());

        Book existingBook = bookService.findBook("1");
        assertNotNull(existingBook);
    }

    @Test
    void testDeleteBookWhenListIsEmptyShouldThrowException() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> bookService.deleteBook("1"));

        assertEquals("El libro con el id 1 no existe, no se puede eliminar", exception.getMessage());
    }

    @Test
    void testUpdateBookTitle() {
        bookService.addBook("1", "Java Basics", "Alice");
        bookService.updateBookTitle("1", "Advanced Java");
        assertEquals("Advanced Java", bookService.findBook("1").getTitle());
    }

    @Test
    void testUpdateBookTitleNotFound() {
        assertThrows(NoSuchElementException.class, () -> bookService.updateBookTitle("99", "New Title"));
    }

    @Test
    void testUpdateBookTitleWhenBookExistsShouldUpdateTitle() {
        bookService.addBook("1", "Java Basics", "Alice");
        bookService.addBook("2", "Python Fundamentals", "Bob");

        bookService.updateBookTitle("1", "Advanced Java");

        assertEquals("Advanced Java", bookService.findBook("1").getTitle());

        assertEquals("Python Fundamentals", bookService.findBook("2").getTitle());

        assertEquals("Alice", bookService.findBook("1").getOwner());
    }

    @Test
    void testUpdateBookTitleWhenBookNotExistsShouldThrowException() {
        bookService.addBook("1", "Java Basics", "Alice");

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> bookService.updateBookTitle("99", "New Title"));

        assertEquals("El libro con el id 99 no existe, no se puede actualizar titulo", exception.getMessage());

        assertEquals("Java Basics", bookService.findBook("1").getTitle());
    }

    @Test
    void testUpdateBookTitleWhenListIsEmptyShouldThrowException() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> bookService.updateBookTitle("1", "New Title"));

        assertEquals("El libro con el id 1 no existe, no se puede actualizar titulo", exception.getMessage());
    }

    @Test
    void testUpdateBookTitleWithEmptyTitleShouldUpdate() {
        bookService.addBook("1", "Java Basics", "Alice");

        bookService.updateBookTitle("1", "");

        assertEquals("", bookService.findBook("1").getTitle());
    }

    @Test
    void testUpdateBookOwner() {
        bookService.addBook("1", "Java Basics", "Alice");
        bookService.updateBookOwner("1", "Bob");
        assertEquals("Bob", bookService.findBook("1").getOwner());
    }

    @Test
    void testUpdateBookOwnerNotFound() {
        assertThrows(NoSuchElementException.class, () -> bookService.updateBookOwner("99", "Charlie"));
    }

    @Test
    void testUpdateBookOwnerWhenBookExistsShouldUpdateOwner() {
        bookService.addBook("1", "Java Basics", "Alice");
        bookService.addBook("2", "Python Fundamentals", "Bob");

        bookService.updateBookOwner("1", "Carol");

        assertEquals("Carol", bookService.findBook("1").getOwner());

        assertEquals("Bob", bookService.findBook("2").getOwner());

        assertEquals("Java Basics", bookService.findBook("1").getTitle());
    }

    @Test
    void testUpdateBookOwnerWhenBookNotExistsShouldThrowException() {
        bookService.addBook("1", "Java Basics", "Alice");
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> bookService.updateBookOwner("99", "Dave"));
        assertEquals("El libro con el id 99 no existe, no se puede actualizar autor", exception.getMessage());
        assertEquals("Alice", bookService.findBook("1").getOwner());
    }

    @Test
    void testUpdateBookOwnerWhenListIsEmptyShouldThrowException() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> bookService.updateBookOwner("1", "Eve"));

        assertEquals("El libro con el id 1 no existe, no se puede actualizar autor", exception.getMessage());
    }

    @Test
    void testUpdateBookOwnerWithEmptyOwnerShouldUpdate() {
        bookService.addBook("1", "Java Basics", "Alice");

        bookService.updateBookOwner("1", "");

        assertEquals("", bookService.findBook("1").getOwner());
    }

    @Test
    void testGetAllBooks() {
        bookService.addBook("1", "Java Basics", "Alice");
        bookService.addBook("2", "Spring Framework", "Bob");
        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }

}
