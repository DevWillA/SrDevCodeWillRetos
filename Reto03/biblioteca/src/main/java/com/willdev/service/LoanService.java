package com.willdev.service;

import java.util.ArrayList;
import java.util.List;


public class LoanService {


    private List<Loans> loan;
    private ManagementBooks books;
    private ManagementUsers users;

    public LoanService(ManagementBooks books, ManagementUsers users) {
        this.loan = new ArrayList<>();
        this.books = books;
        this.users = users;
    }

    public void addLoans(String idUser, String idBook) throws NoSuchElementException, UserNotFoundException {

        Users user = users.findUser(idUser);
        Books book = books.findBook(idBook);

        for (Loans existingLoan : this.loan) {
            if (existingLoan.getBook().getId().equals(idBook)) {
                throw new IllegalStateException(
                        "El libro '" + book.getTitle() + "' ya está prestado por el usuario " +
                                existingLoan.getUser().getName() + " (ID: " + existingLoan.getUser().getId() + ")");
            }
        }

        try {

            var book1 = books.findBook(idBook);
            var user1 = users.findUser(idUser);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("El libro no existe");
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("El usuario no existe");
        }

        if (idUser.isEmpty()) {
            throw new IllegalArgumentException("El id del usuario no puede estar vacio");
        }

        if (idBook.isEmpty()) {
            throw new IllegalArgumentException("El id del libro no puede estar vacio");

        }

        var loan = new Loans(user, book);
        this.loan.add(loan);

    }

    public Loans returLoans(String idUser, String idBook) {

        for (Loans loan : this.loan) {
            if (loan.getUser().getId().equals(idUser) && loan.getBook().getId().equals(idBook)) {
                this.loan.remove(loan);
                return loan;
            }
        }
        throw new NoSuchElementException("El usuario con el id " + i + " no tiene prestamos");

    }

    public Loans getLoans(String idUser) {

        Users user = users.findUser(idUser);

        for (Loans loan : this.loan) {
            if (loan.getUser().getId().equals(idUser)) { 
                return loan;
            }
        }
        throw new NoSuchElementException("El usuario con el id " + idUser + " no tiene prestamos");

    }


}
