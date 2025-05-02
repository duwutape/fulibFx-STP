package de.uniks.stp25;

import de.uniks.stp25.controller.LobbyController;
import de.uniks.stp25.controller.LoginController;
import de.uniks.stp25.model.User;
import de.uniks.stp25.service.ApiService;
import de.uniks.stp25.service.LobbyService;
import de.uniks.stp25.service.LoginService;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;


public class AppTest extends ApplicationTest {
    private App app;
    private Stage stage;

    private ApiService apiService = mock(ApiService.class);

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        app = new App();
        app.start(stage, new LoginController(app, new LoginService(apiService)));
    }

    @Test
    public void testSignUp() {
        when(apiService.createUser(any(), any())).thenReturn(true);
        when(apiService.login(app, "Ash", "S1ch3r35P@55w0rt")).thenReturn(new LobbyController(app, new User("Ash"), new LobbyService(apiService)));
        when(apiService.getOnline()).thenReturn(List.of(new User("Ash"),new User("Test User 1"),new User("Test User 2")));
        when(apiService.logout(any())).thenReturn(true);

        Assertions.assertEquals("SettleTP - Login", stage.getTitle());
        clickOn("#nameField");
        write("Ash");
        clickOn("#passwordField");
        write("S1ch3r35P@55w0rt");
        clickOn("#signUpButton");
        Assertions.assertEquals("SettleTP - Lobby", stage.getTitle());
        clickOn("#logoutButton");
        Assertions.assertEquals("SettleTP - Login", stage.getTitle());
    }

    @Test
    public void testSignIn() {
        when(apiService.login(app, "Ash", "123456")).thenReturn(null);
        when(apiService.login(app, "Ash", "S1ch3r35P@55w0rt")).thenReturn(new LobbyController(app, new User("Ash"), new LobbyService(apiService)));
        when(apiService.getOnline()).thenReturn(List.of(new User("Ash"),new User("Test User 1"),new User("Test User 2")));
        when(apiService.logout(any())).thenReturn(true);

        Assertions.assertEquals("SettleTP - Login", stage.getTitle());
        clickOn("#nameField");
        write("Ash");
        clickOn("#passwordField");
        write("123456");
        clickOn("#signInButton");
        verifyThat("#passwordErrorLabel", hasText("Passwort oder Benutzername stimmen nicht"));
        clickOn("#passwordField");
        for (int i = 0; i < 6; i++) {
            push(KeyCode.BACK_SPACE);
        }
        write("S1ch3r35P@55w0rt");
        clickOn("#signInButton");
        Assertions.assertEquals("SettleTP - Lobby", stage.getTitle());
        clickOn("#logoutButton");
        Assertions.assertEquals("SettleTP - Login", stage.getTitle());
    }

    @Test
    public void testCheckPassword() {
        clickOn("#nameField");
        write("Ash");
        clickOn("#passwordField");
        write("Ash");
        clickOn("#signUpButton");
        verifyThat("#passwordErrorLabel",hasText("Name und Passwort dürfen nicht gleich sein"));

        clickOn("#passwordField");
        write("1234567");
        clickOn("#signUpButton");
        verifyThat("#passwordErrorLabel",hasText("Passwort muss mindestens 8 Zeichen lang sein"));

        clickOn("#passwordField");
        write("12345678");
        clickOn("#signUpButton");
        verifyThat("#passwordErrorLabel",hasText("Passwort muss mindestens eine Großbuchstaben enthalten"));

        clickOn("#passwordField");
        write("1234S678");
        clickOn("#signUpButton");
        verifyThat("#passwordErrorLabel",hasText("Passwort muss mindestens einen Kleinbuchstaben enthalten"));

        clickOn("#passwordField");
        write("SicheresPasswort");
        clickOn("#signUpButton");
        verifyThat("#passwordErrorLabel",hasText("Passwort muss mintestens eine Ziffer enthalten"));

        clickOn("#passwordField");
        write("S1ch3r35P455w0rt");
        clickOn("#signUpButton");
        verifyThat("#passwordErrorLabel",hasText("Passwort muss mindestens ein Sonderzeichen enthalten"));

        clickOn("#passwordField");
        write("S1ch3r35 P@55w0rt");
        clickOn("#signUpButton");
        verifyThat("#passwordErrorLabel",hasText("Passwort darf keine Leerzeichen enthalten"));

        clickOn("#passwordField");
        write("L58jkdjP!");
        clickOn("#signUpButton");
        verifyThat("#passwordErrorLabel",hasText("Passwort darf nicht auf der Blacklist stehen"));
    }
}
