package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.service.LoginService;
import org.fulib.fx.annotation.controller.Controller;
import org.fulib.fx.annotation.controller.Title;
import org.fulib.fx.annotation.event.OnDestroy;
import org.fulib.fx.annotation.event.OnRender;
import org.fulib.fx.controller.Subscriber;

import javax.inject.Inject;

@Title
@Controller()
public class LoginController {
    @Inject
    App app;

    @Inject
    LoginService loginService;

    @Inject
    Subscriber subscriber;

    @Inject
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

    @OnRender
    public void onRender() {
        this.subscriber.bind(signInButton.disableProperty(), passwordField.textProperty().map(pw -> !this.loginService.checkPassword(pw, this.nameField.getText()).equals("Passwort ok")));
    }

    @OnDestroy
    public void onDestroy() {
        this.subscriber.dispose();
    }

    @FXML
    public void signUpClick() {
        String name = nameField.getText();
        String password = passwordField.getText();

        String check = loginService.checkPassword(password, name);
        if (check.equals("Passwort ok")) {
            if (loginService.createAccount(name, password)) {
                app.show("lobby");
            } else {
                passwordField.setText("");
                errorLabel.setText("Account konnte nicht erstellt werden");
                errorLabel.setVisible(true);
            }
        } else {
            passwordField.setText("");
            errorLabel.setText(check);
            errorLabel.setVisible(true);
        }
    }
}
