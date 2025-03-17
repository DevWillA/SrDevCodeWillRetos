package com.willdev.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Loan {

    private User user;
    private Book book;
    private LocalDateTime loanDate;

    public Loan(User user, Book book) {
        this.user = user;
        this.book = book;
        this.loanDate = LocalDateTime.now(); 
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }
    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Préstamo realizado el: " + loanDate.format(formatter) + "\n" +
               "Usuario: " + user.getName() + " (ID: " + user.getId() + ")\n" +
               "Libro: " + book.getTitle() + " (ID: " + book.getId() + ") Autor: " + book.getOwner();
    }

    public Integer size() {
       
    }

}
