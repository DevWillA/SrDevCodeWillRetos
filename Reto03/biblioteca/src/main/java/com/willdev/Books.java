package com.willdev;

public class Books {

    private String id;
    private String title;
    private String owner;

    public Books(String id, String title, String owner) {
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

    public Books findBook(String id) {
        if (this.id.equals(id)) {
            return this;
        }
        throw new NoSuchElementException("El libro con el id " + id + " no existe");
    }

    @Override
    public String toString() {
        return "Books [id=" + id + ", owner=" + owner + ", title=" + title + "]";
    }

}
