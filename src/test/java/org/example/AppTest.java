package de.uniks.ludo;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.example.App;
import org.example.DaggerTestComponent;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class AppTest extends ApplicationTest {

    @Spy
    public final App app = new App();

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        app.setComponent(DaggerTestComponent.builder().mainApp(app).build());
        app.start(stage);
        stage.requestFocus();
    }

    @Test
    public void test() {
        Platform.runLater(() -> app.stage().requestFocus());
        waitForFxEvents();

        clickOn("#nameField");
        write("Ash");
        press(KeyCode.TAB);
        write("Gk#1aaaa");

        clickOn("#signUpButton");

        waitForFxEvents();

        assertEquals("SettleTP - Lobby", app.stage().getTitle());

    }

}