package com.willdev;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Loans {

    private Users user;
    private Books book;
    private LocalDateTime loanDate;

    public Loans(Users user, Books book) {
        this.user = user;
        this.book = book;
        this.loanDate = LocalDateTime.now(); 
    }

    public Users getUser() {
        return user;
    }

    public Books getBook() {
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

}
