package org.example.controller;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.ControllerTest;
import org.example.service.LobbyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(MockitoExtension.class)
public class LobbyControllerTest extends ControllerTest {

    @Spy
    LobbyService lobbyService;

    @InjectMocks
    LobbyController lobbyController;

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        doReturn(List.of()).when(lobbyService).getOnline();
        doReturn(true).when(lobbyService).logout(any());
        app.show(lobbyController);
    }

    @Test
    public void test() {
        doReturn(new VBox()).when(app).show(any(), any());
        // mock LobbyService

        assertEquals("SettleTP - Lobby", app.stage().getTitle());


        clickOn("#logoutButton");

        verify(app, times(1)).show("/");

    }

}