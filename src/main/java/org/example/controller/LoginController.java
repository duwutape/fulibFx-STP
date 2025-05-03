package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.example.App;
import org.example.controller.sub.TestModalComponent;
import org.example.model.User;
import org.example.service.LoginService;
import org.fulib.fx.annotation.controller.Controller;
import org.fulib.fx.annotation.controller.Title;
import org.fulib.fx.annotation.event.OnDestroy;
import org.fulib.fx.annotation.event.OnKey;
import org.fulib.fx.annotation.event.OnRender;
import org.fulib.fx.constructs.Modals;
import org.fulib.fx.controller.Subscriber;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;

@Title
@Controller
public class LoginController {
    @Inject
    App app;

    @Inject
    LoginService loginService;

    @Inject
    Subscriber subscriber;

    @Inject()
    public LoginController() {
    }

    @FXML
    TextField nameField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label errorLabel;

    @FXML
    Button signInButton;

    @FXML
    Button signUpButton;

    @Inject
    Modals modals;

    @Inject
    Provider<TestModalComponent> testModalComponent;

    @OnRender
    public void onRender() {
        this.subscriber.bind(signUpButton.disableProperty(), passwordField.textProperty().map(pw -> this.loginService.checkPassword(pw, this.nameField.getText()) != 0));
    }

    @OnDestroy
    public void onDestroy() {
        this.subscriber.dispose();
    }

    @FXML
    public void signUpClick() {
        String name = nameField.getText();
        String password = passwordField.getText();

        int code = loginService.checkPassword(password, name);
        if (code == 0) {
            User user = loginService.createAccount(name, password);
            if (user != null) {
                app.show("lobby", Map.of("user", user));
            } else {
                passwordField.setText("");
                errorLabel.setText("Account konnte nicht erstellt werden");
                errorLabel.setVisible(true);
            }
        } else {
            passwordField.setText("");
            errorLabel.setText(createErrorMessage(code));
            errorLabel.setVisible(true);
        }
    }

    @FXML
    public void signInClick() {
        String name = nameField.getText();
        String password = passwordField.getText();

        if (!name.isEmpty() && !password.isEmpty()) {
            User user = loginService.login(app, name, password);
            if (user != null) {
                app.show("lobby", Map.of("user", user));
            } else {
                passwordField.setText("");
                errorLabel.setText("Passwort oder Benutzername stimmen nicht");
                errorLabel.setVisible(true);
            }
        }
    }

    private String createErrorMessage(int code) {
        return switch (code) {
            case 1 -> "Name und Passwort dürfen nicht gleich sein";
            case 2 -> "Passwort muss mindestens 8 Zeichen lang sein";
            case 3 -> "Passwort muss mindestens eine Großbuchstaben enthalten";
            case 4 -> "Passwort muss mindestens einen Kleinbuchstaben enthalten";
            case 5 -> "Passwort muss mintestens eine Ziffer enthalten";
            case 6 -> "Passwort muss mindestens ein Sonderzeichen enthalten";
            case 7 -> "Passwort darf keine Leerzeichen enthalten";
            case 8 -> "Passwort darf nicht auf der Blacklist stehen";
            default -> "Something went wrong";
        };
    }

    @OnKey(code = KeyCode.I)
    public void modal() {
        modals.modal(testModalComponent.get()).dialog(false).show();
    }
}
