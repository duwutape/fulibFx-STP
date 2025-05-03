package org.example.service;

import org.example.model.User;

import javax.inject.Inject;
import java.util.List;

public class ApiService {

    @Inject
    public ApiService() {
    }

    public boolean createUser(String name, String password) {
        return true;
    }

    public User login(String name, String password) {
        return new User(name, password);
    }

    public boolean logout(User user) {
        return true;
    }

    public List<User> getOnline() {
        return List.of(new User("Test User 1", null), new User("Test User 2", null), new User("Test User 3", null));
    }
}
