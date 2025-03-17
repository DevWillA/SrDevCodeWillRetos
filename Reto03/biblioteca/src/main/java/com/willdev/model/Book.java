package com.willdev.model;

import com.willdev.exception.NoSuchElementException;

public class Book {

    private String id;
    private String title;
    private String owner;

    public Book(String id, String title, String owner) {
        this.id = id;
        this.title = title;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOwner() {
        return owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Book findBook(String id) {
        if (this.id.equals(id)) {
            return this;
        }
        throw new NoSuchElementException("El libro con el id " + id + " no existe");
    }

    @Override
    public String toString() {
        return"id Libro es: " + id + "\n" +
               "Titulo: " + title + "\n" +
               "Autor: " + owner;
    }

}
