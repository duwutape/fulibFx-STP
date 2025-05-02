package de.uniks.stp25.service;

import de.uniks.stp25.App;
import de.uniks.stp25.Main;
import de.uniks.stp25.controller.LobbyController;
import de.uniks.stp25.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Scanner;

public class LoginService {

    private final ApiService apiService;

    public LoginService() {
        this.apiService = new ApiService();
    }

    public LoginService(ApiService apiService) {
        this.apiService = apiService;
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

    public LobbyController createAccount(App app, String name, String password) {
        if (apiService.createUser(name, password)) {
            return apiService.login(app, name, password);
        } else {
            return null;
        }
    }

    public LobbyController login(App app, String name, String password) {
        return apiService.login(app, name, password);
    }
}
