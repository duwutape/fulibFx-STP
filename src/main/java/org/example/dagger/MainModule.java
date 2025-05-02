package org.example.dagger;

import dagger.Module;
import dagger.Provides;
import org.example.App;
import org.fulib.fx.FulibFxApp;

@Module
public class MainModule {

    @Provides
    FulibFxApp app(App app){
        return app;
    }
}
