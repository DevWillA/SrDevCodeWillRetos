package com.willdev.service;

import java.util.ArrayList;
import java.util.List;

import com.willdev.exception.NoSuchElementException;
import com.willdev.model.Book;

public class BookService {

    private List<Book> books;

    public BookService() {

        books = new ArrayList<>();
    }

    public void addBook(String id, String title, String owner) {

        var book = new Book(id, title, owner);
        books.add(book);

    }

    public Book findBook(String id) {

        for (var book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        throw new NoSuchElementException("El libro con el id " + id + " no existe");
    }

    public void deleteBook(String id) {
        for (var book : books) {
            if (book.getId().equals(id)) {
                books.remove(book);
                return;
            }
        }
        throw new NoSuchElementException("El libro con el id " + id + " no existe, no se puede eliminar");
    }

    public void updateBookTitle(String id, String title) {
        for (var book : books) {
            if (book.getId().equals(id)) {
                book.setTitle(title);
                return;
            }
        }
        throw new NoSuchElementException("El libro con el id " + id + " no existe, no se puede actualizar titulo");
    }

    public void updateBookOwner(String id, String owner) {
        for (var book : books) {
            if (book.getId().equals(id)) {
                book.setOwner(owner);
                return;
            }
        }
        throw new NoSuchElementException("El libro con el id " + id + " no existe, no se puede actualizar autor");
    }

}
