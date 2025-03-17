package com.willdev.service;

import java.util.ArrayList;
import java.util.List;

import com.willdev.exception.UserNotFoundException;
import com.willdev.model.User;

public class UserService {


    private List<User> users;

    public UserService(){

        users = new ArrayList<>();

    }

    public void addUser(String id, String name, String email) {

        var user = new User(id, name, email);
        users.add(user);

    }


    public User findUser(String id) {

        for (var user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new UserNotFoundException("El usuario con el id " + id + " no existe");
    }


    public void deleteUser(String id) {
        for (var user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                return;
            }
        }
        throw new UserNotFoundException("El usuario con el id " + id + " no existe, no se puede eliminar");
    }

    public void updateUserName(String id, String name) {
        for (var user : users) {
            if (user.getId().equals(id)) {
                user.setName(name);
                return;
            }
        }
        throw new UserNotFoundException("El usuario con el id " + id + " no existe, no se puede actualizar nombre");
    }

    public void updateUserEmail(String id, String email) {
        for (var user : users) {
            if (user.getId().equals(id)) {
                user.setEmail(email);
                return;
            }
        }
        throw new UserNotFoundException("El usuario con el id " + id + " no existe, no se puede actualizar correo");
    }
}
