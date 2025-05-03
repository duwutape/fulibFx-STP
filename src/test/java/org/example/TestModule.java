package org.example;

import dagger.Module;
import dagger.Provides;
import org.example.model.User;
import org.example.service.ApiService;

@Module
public class TestModule {

    @Provides
    ApiService apiService() {
        return new ApiService() {

            @Override
            public User login(String name, String password) {
                return new User(name, password);
            }
        };
    }

}