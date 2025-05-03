package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import org.example.App;
import org.example.model.User;
import org.example.service.LobbyService;
import org.fulib.fx.annotation.controller.Controller;
import org.fulib.fx.annotation.controller.Title;
import org.fulib.fx.annotation.event.OnRender;
import org.fulib.fx.annotation.param.Param;

import javax.inject.Inject;
import java.util.List;

@Title
@Controller
public class LobbyController {

    @Inject
    App app;

    @Param("user")
    User user;

    @Inject
    LobbyService lobbyService;

    @Inject
    public LobbyController() {
    }

    @FXML
    Button logoutButton;

    @FXML
    VBox onlineUserBox;

    @OnRender
    public void onRender() {
        createUserBox();
    }

    @FXML
    public void onLogoutClick() {
        if(lobbyService.logout(user)){
            app.show("/");
        }
    }

    private void createUserBox(){
        List<User> onlineUsers = lobbyService.getOnline();
        for (User user : onlineUsers) {
            Label label = new Label(user.name());
            onlineUserBox.getChildren().addAll(label, new Separator());
        }
    }
}
