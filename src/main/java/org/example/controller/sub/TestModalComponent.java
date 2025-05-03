package org.example.controller.sub;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.App;
import org.fulib.fx.annotation.controller.Component;
import org.fulib.fx.annotation.event.OnRender;
import org.fulib.fx.annotation.param.Param;

import javax.inject.Inject;

@Component
public class TestModalComponent extends VBox {

    @Inject
    App app;

    @Param("modalStage")
    Stage modalStage;

    @Inject
    public TestModalComponent() {
    }

     @OnRender
    public void onRender() {
        Label title = new Label("Test");
        Label text = new Label("Lorem ipsum und so weiter");
        Button button = new Button("close");
        button.setOnAction(event -> modalStage.close());
        this.getChildren().addAll(title,text,button);
     }
}
