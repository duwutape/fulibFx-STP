package org.example;

import dagger.Component;
import org.example.dagger.MainComponent;
import org.example.dagger.MainModule;

import javax.inject.Singleton;

@Component(modules = {MainModule.class, TestModule.class})
@Singleton
public interface TestComponent extends MainComponent {

    @Component.Builder
    interface Builder extends MainComponent.Builder {
        TestComponent build();
    }
}