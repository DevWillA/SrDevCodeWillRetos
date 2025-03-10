package com.willdev;

import java.util.ArrayList;
import java.util.List;

public class ManagementLoans {

    private List<Loans> loan;
    private ManagementBooks books;
    private ManagementUsers users;

    public ManagementLoans(ManagementBooks books, ManagementUsers users) {
        this.books = books;
        this.users = users;
        this.loan = new ArrayList<>();
    }

    public void addLoans(String idUser, String idBook) throws BookNotFoundException, UserNotFoundException {

        try {

            var book = books.findBook(idBook);
            var user = users.findUser(idUser);
        } catch (BookNotFoundException e) {
            throw new BookNotFoundException("El libro no existe");
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("El usuario no existe");
        }

        if (idUser.isEmpty()) {
            throw new IllegalArgumentException("El id del usuario no puede estar vacio");
        }

        if (idBook.isEmpty()) {
            throw new IllegalArgumentException("El id del libro no puede estar vacio");

        }

        var loan = new Loans(idUser, idBook);
        this.loan.add(loan);

    }

    public Loans getLoans(String idUser) {

        var user = users.findUser(idUser);

        for (Loans loan : this.loan) {
            if (loan.getIdUser().equals(idUser)) {
                return loan;
            }
        }
        return null;

    }

}
