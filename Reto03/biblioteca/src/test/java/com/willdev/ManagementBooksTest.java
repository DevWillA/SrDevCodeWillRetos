package com.willdev;

import org.junit.jupiter.api.Test;

public class ManagementBooksTest {

    @Test
    void testAddBook() {
        ManagementBooks managementBooks = new ManagementBooks();
        managementBooks.addBook("1", "El principito", "Will");
        managementBooks.addBook("2", "El principito", "Will");
        managementBooks.addBook("3", "El principito", "Will");
    }

    @Test
    void testFindBook() {
        ManagementBooks managementBooks = new ManagementBooks();
        managementBooks.addBook("1", "El principito", "Will");
        managementBooks.addBook("2", "El principito", "Will");
        managementBooks.addBook("3", "El principito", "Will");
        managementBooks.findBook("1");
    }

    
}
