package org.example.dagger;

import dagger.BindsInstance;
import dagger.Component;
import org.example.App;
import org.example.Routing;

import javax.inject.Singleton;

@Component(modules = {MainModule.class})
@Singleton
public interface MainComponent {
    Routing routes();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder mainApp(App app);

        MainComponent build();
    }
}
