package com.willdev.service;

import java.util.ArrayList;
import java.util.List;

import com.willdev.exception.NoSuchElementException;
import com.willdev.exception.UserNotFoundException;
import com.willdev.model.Book;
import com.willdev.model.Loan;
import com.willdev.model.User;

public class LoanService {

    private List<Loan> loan;
    private BookService books;
    private UserService users;

    public LoanService(BookService books, UserService users) {
        this.loan = new ArrayList<>();
        this.books = books;
        this.users = users;
    }

    public void addLoan(String idUser, String idBook) throws NoSuchElementException, UserNotFoundException {
        if (idUser == null || idBook == null) {
            throw new IllegalArgumentException("ID de usuario o libro no puede ser null");
        }
    
        try {
            User user = users.findUser(idUser);
            Book book = books.findBook(idBook);
    
            for (Loan existingLoan : this.loan) {
                if (existingLoan.getBook().getId().equals(idBook)) {
                    throw new IllegalStateException(
                        "El libro '" + book.getTitle() + "' ya está prestado por el usuario " +
                        existingLoan.getUser().getName() + " (ID: " + existingLoan.getUser().getId() + ")");
                }
            }
    
            this.loan.add(new Loan(user, book));
            
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("El libro con ID " + idBook + " no existe");
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("El usuario con ID " + idUser + " no existe");
        }
    }

    public Loan returnBook(String idUser, String idBook) {

        for (Loan loan : this.loan) {
            if (loan.getUser().getId().equals(idUser) && loan.getBook().getId().equals(idBook)) {
                this.loan.remove(loan);
                return loan;
            }
        }
        throw new NoSuchElementException("El usuario con el id " + idUser + " no tiene prestamos");

    }

    public Loan getLoanByUser(String idUser) {

        for (Loan loan : this.loan) {
            if (loan.getUser().getId().equals(idUser)) {
                return loan;
            }
        }
        throw new NoSuchElementException("El usuario con el id " + idUser + " no tiene prestamos");

    }

    public Loan getLoanByBook(String idBook) {

        for (Loan loan : this.loan) {
            if (loan.getBook().getId().equals(idBook)) {
                return loan;
            }
        }
        throw new NoSuchElementException("El Libro con el id " + idBook + " no tiene prestamos");

    }

}
