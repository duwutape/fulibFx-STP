package org.example;

import org.example.controller.LobbyController;
import org.example.controller.LoginController;
import org.fulib.fx.annotation.Route;

import javax.inject.Inject;
import javax.inject.Provider;

public class Routing {

    @Inject
    public Routing() {

    }

    @Inject
    @Route("")
    public Provider<LoginController> loginController;

    @Inject
    @Route("lobby")
    public Provider<LobbyController> lobbyController;
}
