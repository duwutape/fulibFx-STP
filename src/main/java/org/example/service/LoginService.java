package org.example.service;

import org.example.App;
import org.example.Main;
import org.example.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Scanner;

@Singleton
public class LoginService {

    @Inject
    ApiService apiService;

    @Inject
    public LoginService() {
    }

    public int checkPassword(String password, String name) {
        if (password.equals(name)) {
            return 1;
        } else if (password.length() < 8) {
            return 2;
        } else if (!password.matches(".*[A-Z].*")) {
            return 3;
        } else if (!password.matches(".*[a-z].*")) {
            return 4;
        } else if (!password.matches(".*\\d.*")) {
            return 5;
        } else if (!password.matches(".*[!@#$%^&*].*")) {
            return 6;
        } else if (password.matches(".*\\s.*")) {
            return 7;
        } else if (inBlacklist(password)){
            return 8;
        } else {
            return 0;
        }
    }

    private boolean inBlacklist(String password) {
        try {
            File blacklist = new File(Objects.requireNonNull(Main.class.getResource("10-million-password-list-top-100000.txt")).toURI());
            Scanner scanner = new Scanner(blacklist);
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public User createAccount(String name, String password) {
        if (apiService.createUser(name, password)) {
            return apiService.login(name, password);
        } else {
            return null;
        }
    }

    public User login(App app, String name, String password) {
        return apiService.login(name, password);
    }
}
