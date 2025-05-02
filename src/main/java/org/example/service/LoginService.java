package org.example.service;

import org.example.App;
import org.example.Main;

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

    public String checkPassword(String password, String name) {
        if (password.equals(name)) {
            return "Name und Passwort dürfen nicht gleich sein";
        } else if (password.length() < 8) {
            return "Passwort muss mindestens 8 Zeichen lang sein";
        } else if (!password.matches(".*[A-Z].*")) {
            return "Passwort muss mindestens eine Großbuchstaben enthalten";
        } else if (!password.matches(".*[a-z].*")) {
            return "Passwort muss mindestens einen Kleinbuchstaben enthalten";
        } else if (!password.matches(".*\\d.*")) {
            return "Passwort muss mintestens eine Ziffer enthalten";
        } else if (!password.matches(".*[!@#$%^&*].*")) {
            return "Passwort muss mindestens ein Sonderzeichen enthalten";
        } else if (password.matches(".*\\s.*")) {
            return "Passwort darf keine Leerzeichen enthalten";
        } else if (inBlacklist(password)){
            return "Passwort darf nicht auf der Blacklist stehen";
        } else {
            return "Passwort ok";
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

    public boolean createAccount(String name, String password) {
        if (apiService.createUser(name, password)) {
            return apiService.login(name, password);
        } else {
            return false;
        }
    }

    public boolean login(App app, String name, String password) {
        return apiService.login(name, password);
    }
}
