package com.willdev;

import java.util.ArrayList;
import java.util.List;

public class ManagementUsers {


    private List<Users> users;

    public ManagementUsers(){

        users = new ArrayList<>();

    }

    public void addUser(String id, String name) {

        var user = new Users(id, name);
        users.add(user);

    }


    public Users findUser(String id) {

        for (var user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new UserNotFoundException("El ususario con el id " + id + " no existe");
    }
}
