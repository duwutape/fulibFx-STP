package org.example.service;

import org.example.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class LobbyService {

    @Inject
    ApiService apiService;

    @Inject
    public LobbyService() {

    }

    public boolean logout(User user) {
        return apiService.logout(user);
    }

    public List<User> getOnline() {
        return apiService.getOnline();
    }
}
