package com.willdev.service;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private List<Books> books;

    public BookService(){

        books = new ArrayList<>();
    }

    public void addBook (String id, String title, String owner) {
            
            var book = new Books(id, title, owner);
            books.add(book);

    }

    public Books findBook(String id) {

        for (var book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        throw new NoSuchElementException("El libro con el id " + id + " no existe");
    }


}
