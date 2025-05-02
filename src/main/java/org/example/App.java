package org.example;

import javafx.stage.Stage;
import org.example.dagger.DaggerMainComponent;
import org.example.dagger.MainComponent;
import org.fulib.fx.FulibFxApp;

import java.nio.file.Path;
import java.util.logging.Level;

public class App extends FulibFxApp {

    private MainComponent component;

    public App() {
        super();
        this.component = DaggerMainComponent.builder().mainApp(this).build();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            super.start(primaryStage);
            setResourcesPath(Path.of("src/main/resources"));
            autoRefresher().setup(Path.of("src/main/resources"));
            setTitlePattern("SettleTP - %s");

            registerRoutes(component.routes());
            show("");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while starting the application: " + e.getMessage(), e);
        }
    }

    public App setComponent(MainComponent component) {
        this.component = component;
        return this;
    }
}
