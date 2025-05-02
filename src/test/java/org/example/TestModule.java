package org.example;

import dagger.Module;
import dagger.Provides;
import org.example.service.ApiService;

@Module
public class TestModule {

    @Provides
    ApiService apiService() {
        return new ApiService() {

            @Override
            public boolean login(String name, String password) {
                return false;
            }
        };
    }

}