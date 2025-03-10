package com.willdev;

public class Main {
    public static void main(String[] args) {
        
        var users = new ManagementUsers();
        users.addUser("1", "William");
        users.addUser("2", "Yuly");
        users.addUser("3", "Patricia");

        var books = new ManagementBooks();
        books.addBook("1", "Java", "William");
        books.addBook("2", "Python", "Yuly");
        books.addBook("3", "C#", "Patricia");

        var loans = new ManagementLoans(books, users);
        try {
            loans.addLoans("1", "1");
            loans.addLoans("2", "2");
            loans.addLoans("3", "3");
            loans.addLoans("5", "1");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Gracias por usar nuestro servicio");

    }
}