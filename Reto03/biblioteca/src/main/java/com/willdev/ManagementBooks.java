package com.willdev;

import java.util.ArrayList;
import java.util.List;

public class ManagementBooks {

    private List<Books> books;

    public ManagementBooks(){

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
        throw new BookNotFoundException("El libro con el id " + id + " no existe");
    }


}
