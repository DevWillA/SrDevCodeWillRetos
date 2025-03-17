package com.willdev.model;

public class User {

    private String id;
    private String name;
    private String email;

    
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }


    public String getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public User findUser(String id) {
        if (this.id.equals(id)) {
            return this;
        }
        throw new UserNotFoundException("El usuario con el id " + id + " no existe");
    }

    @Override
    public String toString() {

        return "id Usuario es: " + id + "\n" +
               "Nombre: " + name + "\n" +
               "Email: " + email;
    }


}
