package com.willdev.model;

public class User {

    private String id;
    private String name;

    
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public User findUser(String id) {
        if (this.id.equals(id)) {
            return this;
        }
        throw new UserNotFoundException("El usuario con el id " + id + " no existe");
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", name=" + name + "]";
    }


}
